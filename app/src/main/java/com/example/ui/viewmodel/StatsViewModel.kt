package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.CompoundMasteryEntity
import com.example.data.local.QuizSessionResultEntity
import com.example.data.model.LeaderboardUser
import com.example.data.repository.QuizMasterRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class StatsDashboardData(
  val totalGames: Int = 0,
  val totalQuestionsAnswered: Int = 0,
  val totalCorrectAnswers: Int = 0,
  val overallAccuracyPercent: Int = 0,
  val organicAccuracyPercent: Int = 0,
  val inorganicAccuracyPercent: Int = 0,
  val totalXp: Int = 350,
  val currentRankTitle: String = "Investigador Químico",
  val currentRankLevel: Int = 1,
  val currentStreakDays: Int = 3,
  val duelWins: Int = 0,
  val duelLosses: Int = 0,
  val masteredCompounds: List<CompoundMasteryEntity> = emptyList(),
  val reviewCompounds: List<CompoundMasteryEntity> = emptyList(),
  val recentHistory: List<QuizSessionResultEntity> = emptyList(),
  val leaderboard: List<LeaderboardUser> = emptyList()
)

class StatsViewModel(private val repository: QuizMasterRepository) : ViewModel() {

  val dashboardData: StateFlow<StatsDashboardData> = combine(
    repository.quizResults,
    repository.compoundMastery
  ) { results, masteryList ->
    val totalGames = results.size
    val totalQ = results.sumOf { it.totalQuestions }
    val totalScore = results.sumOf { it.score }
    val earnedXp = results.sumOf { it.xpEarned } + 350 // Base onboarding XP

    val duelGames = results.filter { it.isDuel }
    val duelWins = duelGames.count { it.wonDuel == true }
    val duelLosses = duelGames.count { it.wonDuel == false }

    val totalCorrectCount = masteryList.sumOf { it.correctCount }
    val totalIncorrectCount = masteryList.sumOf { it.incorrectCount }
    val totalAnswers = totalCorrectCount + totalIncorrectCount

    val accuracy = if (totalAnswers > 0) {
      ((totalCorrectCount.toFloat() / totalAnswers) * 100).toInt()
    } else 0

    val organicItems = masteryList.filter { it.type == "ORGANIC" }
    val organicCorrect = organicItems.sumOf { it.correctCount }
    val organicTotal = organicCorrect + organicItems.sumOf { it.incorrectCount }
    val organicAcc = if (organicTotal > 0) ((organicCorrect.toFloat() / organicTotal) * 100).toInt() else 0

    val inorganicItems = masteryList.filter { it.type == "INORGANIC" }
    val inorganicCorrect = inorganicItems.sumOf { it.correctCount }
    val inorganicTotal = inorganicCorrect + inorganicItems.sumOf { it.incorrectCount }
    val inorganicAcc = if (inorganicTotal > 0) ((inorganicCorrect.toFloat() / inorganicTotal) * 100).toInt() else 0

    val rankTitle = when {
      earnedXp >= 4000 -> "Premio Nobel de Química"
      earnedXp >= 3000 -> "Gran Alquimista Sintético"
      earnedXp >= 2000 -> "Maestro de Reacciones"
      earnedXp >= 1000 -> "Analista de Laboratorio"
      earnedXp >= 500 -> "Investigador Químico"
      else -> "Novicio del Matraz"
    }

    val rankLevel = when {
      earnedXp >= 4000 -> 5
      earnedXp >= 3000 -> 4
      earnedXp >= 2000 -> 3
      earnedXp >= 1000 -> 2
      else -> 1
    }

    val mastered = masteryList.filter { it.isMastered }
    val toReview = masteryList.filter { !it.isMastered && it.incorrectCount > 0 }

    val leaderboard = repository.getGlobalLeaderboard(earnedXp)

    StatsDashboardData(
      totalGames = totalGames,
      totalQuestionsAnswered = totalAnswers,
      totalCorrectAnswers = totalCorrectCount,
      overallAccuracyPercent = accuracy,
      organicAccuracyPercent = organicAcc,
      inorganicAccuracyPercent = inorganicAcc,
      totalXp = earnedXp,
      currentRankTitle = rankTitle,
      currentRankLevel = rankLevel,
      currentStreakDays = if (totalGames > 0) (totalGames % 5) + 2 else 2,
      duelWins = duelWins,
      duelLosses = duelLosses,
      masteredCompounds = mastered,
      reviewCompounds = toReview,
      recentHistory = results.take(10),
      leaderboard = leaderboard
    )
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = StatsDashboardData(
      leaderboard = repository.getGlobalLeaderboard(350)
    )
  )
}
