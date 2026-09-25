package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.CompoundType
import com.example.data.model.DifficultyLevel
import com.example.data.model.DuelRoundState
import com.example.data.model.DuelStatus
import com.example.data.model.PowerUpType
import com.example.data.model.PresetQuestions
import com.example.data.model.TriviaQuestion
import com.example.data.repository.QuizMasterRepository
import kotlin.random.Random
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OpponentProfile(
  val name: String,
  val institution: String,
  val avatar: String,
  val eloRating: Int,
  val skillAccuracy: Float
)

data class DuelUiState(
  val status: DuelStatus = DuelStatus.MATCHMAKING,
  val opponent: OpponentProfile = OpponentProfile("Buscando...", "Servidor Global", "⏳", 1200, 0.75f),
  val questions: List<TriviaQuestion> = emptyList(),
  val currentRoundIndex: Int = 0,
  val roundTimeLeftSeconds: Int = 12,
  val userTotalScore: Int = 0,
  val opponentTotalScore: Int = 0,
  val userStreak: Int = 0,
  val userSelectedChoice: Int? = null,
  val opponentSelectedChoice: Int? = null,
  val isOpponentAnswering: Boolean = false,
  val opponentAnsweredText: String = "",
  val eliminatedOptions: Set<Int> = emptySet(),
  val hintRevealed: String? = null,
  // Power-up availability
  val isFiftyFiftyUsed: Boolean = false,
  val isCatalystUsed: Boolean = false,
  val isSpectrometryUsed: Boolean = false,
  val roundHistory: List<DuelRoundState> = emptyList(),
  val showExplanationSheet: Boolean = false,
  val explanationReviewIndex: Int = 0
)

class DuelViewModel(private val repository: QuizMasterRepository) : ViewModel() {

  private val _state = MutableStateFlow(DuelUiState())
  val state: StateFlow<DuelUiState> = _state.asStateFlow()

  private var roundTimerJob: Job? = null
  private var opponentAiJob: Job? = null

  private val simulatedOpponents = listOf(
    OpponentProfile("Clara Mendonça", "Univ. de Coímbra", "👩‍🔬", 1450, 0.82f),
    OpponentProfile("Liam O'Connor", "Trinity College Dublin", "👨‍🔬", 1380, 0.75f),
    OpponentProfile("Valeria Santos", "UNAM Lab Central", "🧬", 1520, 0.88f),
    OpponentProfile("Mateo Bellini", "Politécnico di Milano", "⚗️", 1290, 0.70f),
    OpponentProfile("Yuki Tanaka", "Kyoto Chem Institute", "🔬", 1490, 0.85f)
  )

  fun startMatchmaking(customFriendCode: String? = null) {
    roundTimerJob?.cancel()
    opponentAiJob?.cancel()

    val randomOpponent = if (!customFriendCode.isNullOrBlank()) {
      OpponentProfile("Amigo #$customFriendCode", "Sala Privada", "🤝", 1350, 0.78f)
    } else {
      simulatedOpponents.random()
    }

    _state.value = DuelUiState(
      status = DuelStatus.MATCHMAKING,
      opponent = randomOpponent
    )

    // Simulate matchmaking search time (1.8 seconds)
    viewModelScope.launch {
      delay(1800)
      // Pick 5 varied questions (organic and inorganic mix)
      val duelQuestions = PresetQuestions.allQuestions.shuffled().take(5)

      _state.update {
        it.copy(
          status = DuelStatus.COUNTDOWN,
          questions = duelQuestions,
          currentRoundIndex = 0,
          userTotalScore = 0,
          opponentTotalScore = 0,
          userStreak = 0,
          isFiftyFiftyUsed = false,
          isCatalystUsed = false,
          isSpectrometryUsed = false,
          roundHistory = emptyList()
        )
      }

      // 1.5s countdown before first round begins
      delay(1500)
      startRound(0)
    }
  }

  private fun startRound(roundIdx: Int) {
    val questions = _state.value.questions
    if (roundIdx >= questions.size) {
      finishMatch()
      return
    }

    _state.update {
      it.copy(
        status = DuelStatus.ROUND_ACTIVE,
        currentRoundIndex = roundIdx,
        roundTimeLeftSeconds = 12,
        userSelectedChoice = null,
        opponentSelectedChoice = null,
        isOpponentAnswering = true,
        opponentAnsweredText = "Analizando compuesto...",
        eliminatedOptions = emptySet(),
        hintRevealed = null,
        showExplanationSheet = false
      )
    }

    // Launch round timer
    roundTimerJob?.cancel()
    roundTimerJob = viewModelScope.launch {
      while (_state.value.roundTimeLeftSeconds > 0) {
        delay(1000)
        _state.update { it.copy(roundTimeLeftSeconds = it.roundTimeLeftSeconds - 1) }
        if (_state.value.roundTimeLeftSeconds == 0) {
          evaluateRoundEnd()
          break
        }
      }
    }

    // Launch simulated opponent response
    opponentAiJob?.cancel()
    opponentAiJob = viewModelScope.launch {
      val opponent = _state.value.opponent
      val currentQ = questions[roundIdx]

      // Opponent takes between 3 to 7 seconds to answer
      val thinkTimeMs = Random.nextLong(3000, 7500)
      delay(thinkTimeMs)

      if (_state.value.status == DuelStatus.ROUND_ACTIVE) {
        val willAnswerCorrectly = Random.nextFloat() <= opponent.skillAccuracy
        val chosen = if (willAnswerCorrectly) {
          currentQ.correctIndex
        } else {
          // pick a wrong option
          val wrongIndices = (0..3).filter { it != currentQ.correctIndex }
          wrongIndices.random()
        }

        _state.update {
          it.copy(
            opponentSelectedChoice = chosen,
            isOpponentAnswering = false,
            opponentAnsweredText = "¡Ha respondido!"
          )
        }

        // If user has also answered, conclude round without waiting for timer to expire
        if (_state.value.userSelectedChoice != null) {
          delay(800)
          evaluateRoundEnd()
        }
      }
    }
  }

  fun submitUserAnswer(choiceIndex: Int) {
    if (_state.value.status != DuelStatus.ROUND_ACTIVE || _state.value.userSelectedChoice != null) return

    _state.update { it.copy(userSelectedChoice = choiceIndex) }

    // If opponent has already answered, conclude round quickly
    if (_state.value.opponentSelectedChoice != null) {
      viewModelScope.launch {
        delay(800)
        evaluateRoundEnd()
      }
    }
  }

  private fun evaluateRoundEnd() {
    roundTimerJob?.cancel()
    opponentAiJob?.cancel()

    val current = _state.value
    val q = current.questions[current.currentRoundIndex]

    val userCorrect = current.userSelectedChoice == q.correctIndex
    val oppChoice = current.opponentSelectedChoice ?: (0..3).filter { it != q.correctIndex }.random()
    val oppCorrect = oppChoice == q.correctIndex

    val userPoints = if (userCorrect) {
      100 + (current.roundTimeLeftSeconds * 8) + (current.userStreak * 20)
    } else 0

    val oppPoints = if (oppCorrect) {
      100 + (Random.nextInt(4, 9) * 8)
    } else 0

    val newStreak = if (userCorrect) current.userStreak + 1 else 0

    val roundState = DuelRoundState(
      question = q,
      userChoice = current.userSelectedChoice,
      opponentChoice = oppChoice,
      userScoreEarned = userPoints,
      opponentScoreEarned = oppPoints
    )

    _state.update {
      it.copy(
        status = DuelStatus.ROUND_RESULT,
        opponentSelectedChoice = oppChoice,
        userTotalScore = it.userTotalScore + userPoints,
        opponentTotalScore = it.opponentTotalScore + oppPoints,
        userStreak = newStreak,
        roundHistory = it.roundHistory + roundState
      )
    }

    // Record question in Room for mastery tracking
    viewModelScope.launch {
      repository.recordQuestionAnswer(
        formula = q.formula,
        commonName = q.compoundName,
        type = q.type,
        isCorrect = userCorrect
      )
    }

    // Auto proceed to next round after 3.5 seconds
    viewModelScope.launch {
      delay(3500)
      startRound(current.currentRoundIndex + 1)
    }
  }

  private fun finishMatch() {
    val current = _state.value
    val won = current.userTotalScore > current.opponentTotalScore
    val xpEarned = if (won) 150 else 60

    _state.update {
      it.copy(
        status = DuelStatus.MATCH_FINISHED
      )
    }

    viewModelScope.launch {
      repository.recordQuizResult(
        title = "Duelo 1v1 vs ${current.opponent.name}",
        category = CompoundType.ALL,
        difficulty = DifficultyLevel.ADVANCED,
        score = current.userTotalScore,
        total = current.questions.size,
        xpEarned = xpEarned,
        isDuel = true,
        duelOpponent = current.opponent.name,
        wonDuel = won
      )
    }
  }

  // Power-ups
  fun useFiftyFifty() {
    val current = _state.value
    if (current.isFiftyFiftyUsed || current.status != DuelStatus.ROUND_ACTIVE) return

    val q = current.questions[current.currentRoundIndex]
    val wrongOptions = (0..3).filter { it != q.correctIndex }.shuffled().take(2).toSet()

    _state.update {
      it.copy(
        isFiftyFiftyUsed = true,
        eliminatedOptions = wrongOptions
      )
    }
  }

  fun useCatalyst() {
    val current = _state.value
    if (current.isCatalystUsed || current.status != DuelStatus.ROUND_ACTIVE) return

    _state.update {
      it.copy(
        isCatalystUsed = true,
        roundTimeLeftSeconds = it.roundTimeLeftSeconds + 6
      )
    }
  }

  fun useSpectrometry() {
    val current = _state.value
    if (current.isSpectrometryUsed || current.status != DuelStatus.ROUND_ACTIVE) return

    val q = current.questions[current.currentRoundIndex]
    _state.update {
      it.copy(
        isSpectrometryUsed = true,
        hintRevealed = q.hint.ifEmpty { "Pista: Analiza el número de oxidación y enlaces π." }
      )
    }
  }

  fun showReviewExplanation(index: Int) {
    _state.update {
      it.copy(
        showExplanationSheet = true,
        explanationReviewIndex = index
      )
    }
  }

  fun dismissReviewExplanation() {
    _state.update { it.copy(showExplanationSheet = false) }
  }

  override fun onCleared() {
    super.onCleared()
    roundTimerJob?.cancel()
    opponentAiJob?.cancel()
  }
}
