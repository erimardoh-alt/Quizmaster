package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.CompoundType
import com.example.data.model.DifficultyLevel
import com.example.data.model.PresetQuestions
import com.example.data.model.TriviaQuestion
import com.example.data.repository.QuizMasterRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class QuizPlayState(
  val questions: List<TriviaQuestion> = emptyList(),
  val currentIndex: Int = 0,
  val selectedAnswerIndex: Int? = null,
  val isAnswerSubmitted: Boolean = false,
  val isCorrect: Boolean = false,
  val score: Int = 0,
  val streak: Int = 0,
  val maxStreak: Int = 0,
  val timeRemainingSeconds: Int = 20,
  val isTimerActive: Boolean = false,
  val isGameOver: Boolean = false,
  val xpGained: Int = 0,
  val currentCategory: CompoundType = CompoundType.ALL,
  val currentDifficulty: DifficultyLevel = DifficultyLevel.BEGINNER,
  val quizTitle: String = "Trivia de Química",
  val eliminatedOptionIndices: Set<Int> = emptySet(),
  val showExplanationSheet: Boolean = false
)

class QuizViewModel(private val repository: QuizMasterRepository) : ViewModel() {

  private val _state = MutableStateFlow(QuizPlayState())
  val state: StateFlow<QuizPlayState> = _state.asStateFlow()

  private var timerJob: Job? = null

  fun startPresetQuiz(
    category: CompoundType = CompoundType.ALL,
    difficulty: DifficultyLevel = DifficultyLevel.BEGINNER
  ) {
    val pool = PresetQuestions.getQuestionsByFilter(category, difficulty)
    val selected = pool.shuffled().take(6).ifEmpty {
      PresetQuestions.allQuestions.shuffled().take(6)
    }

    _state.value = QuizPlayState(
      questions = selected,
      currentIndex = 0,
      currentCategory = category,
      currentDifficulty = difficulty,
      quizTitle = "${category.displayName} • ${difficulty.title}",
      timeRemainingSeconds = 25,
      isTimerActive = true
    )
    startTimer()
  }

  fun startCustomQuiz(
    title: String,
    category: CompoundType,
    difficulty: DifficultyLevel,
    questions: List<TriviaQuestion>
  ) {
    if (questions.isEmpty()) return
    _state.value = QuizPlayState(
      questions = questions,
      currentIndex = 0,
      currentCategory = category,
      currentDifficulty = difficulty,
      quizTitle = title,
      timeRemainingSeconds = 25,
      isTimerActive = true
    )
    startTimer()
  }

  private fun startTimer() {
    timerJob?.cancel()
    timerJob = viewModelScope.launch {
      while (_state.value.isTimerActive && _state.value.timeRemainingSeconds > 0) {
        delay(1000)
        _state.update { curr ->
          if (curr.timeRemainingSeconds <= 1 && !curr.isAnswerSubmitted) {
            // Time out: automatically register timeout
            curr.copy(timeRemainingSeconds = 0, isTimerActive = false)
          } else {
            curr.copy(timeRemainingSeconds = curr.timeRemainingSeconds - 1)
          }
        }
        if (_state.value.timeRemainingSeconds == 0 && !_state.value.isAnswerSubmitted) {
          submitAnswer(-1) // Timeout
          break
        }
      }
    }
  }

  fun selectOption(index: Int) {
    if (_state.value.isAnswerSubmitted) return
    _state.update { it.copy(selectedAnswerIndex = index) }
  }

  fun submitAnswer(chosenIndex: Int = _state.value.selectedAnswerIndex ?: -1) {
    val current = _state.value
    if (current.isAnswerSubmitted || current.questions.isEmpty()) return

    timerJob?.cancel()
    val question = current.questions[current.currentIndex]
    val isCorrect = chosenIndex == question.correctIndex

    val newStreak = if (isCorrect) current.streak + 1 else 0
    val newMaxStreak = maxOf(current.maxStreak, newStreak)
    val scoreDelta = if (isCorrect) (100 + (newStreak * 15) + (current.timeRemainingSeconds * 2)) else 0
    val newScore = current.score + scoreDelta

    _state.update {
      it.copy(
        selectedAnswerIndex = chosenIndex,
        isAnswerSubmitted = true,
        isCorrect = isCorrect,
        score = newScore,
        streak = newStreak,
        maxStreak = newMaxStreak,
        isTimerActive = false,
        showExplanationSheet = true
      )
    }

    // Persist mastery in background
    viewModelScope.launch {
      repository.recordQuestionAnswer(
        formula = question.formula,
        commonName = question.compoundName,
        type = question.type,
        isCorrect = isCorrect
      )
    }
  }

  fun dismissExplanation() {
    _state.update { it.copy(showExplanationSheet = false) }
  }

  fun nextQuestion() {
    val current = _state.value
    if (current.currentIndex + 1 < current.questions.size) {
      _state.update {
        it.copy(
          currentIndex = it.currentIndex + 1,
          selectedAnswerIndex = null,
          isAnswerSubmitted = false,
          isCorrect = false,
          timeRemainingSeconds = 25,
          isTimerActive = true,
          eliminatedOptionIndices = emptySet(),
          showExplanationSheet = false
        )
      }
      startTimer()
    } else {
      // Quiz Finished!
      finishQuiz()
    }
  }

  private fun finishQuiz() {
    timerJob?.cancel()
    val current = _state.value
    val correctCount = current.score / 100 // approximate or exact count
    val earnedXp = (current.score / 5).coerceAtLeast(30)

    _state.update {
      it.copy(
        isGameOver = true,
        isTimerActive = false,
        xpGained = earnedXp,
        showExplanationSheet = false
      )
    }

    viewModelScope.launch {
      repository.recordQuizResult(
        title = current.quizTitle,
        category = current.currentCategory,
        difficulty = current.currentDifficulty,
        score = current.score,
        total = current.questions.size,
        xpEarned = earnedXp
      )
    }
  }

  fun resetQuiz() {
    timerJob?.cancel()
    _state.value = QuizPlayState()
  }

  override fun onCleared() {
    super.onCleared()
    timerJob?.cancel()
  }
}
