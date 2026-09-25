package com.example.data.repository

import com.example.data.local.CompoundMasteryEntity
import com.example.data.local.CustomQuestionEntity
import com.example.data.local.CustomQuizEntity
import com.example.data.local.QuizMasterDao
import com.example.data.local.QuizSessionResultEntity
import com.example.data.model.CompoundType
import com.example.data.model.DifficultyLevel
import com.example.data.model.LeaderboardUser
import com.example.data.model.TriviaQuestion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuizMasterRepository(private val dao: QuizMasterDao) {

  val customQuizzes: Flow<List<CustomQuizEntity>> = dao.getAllCustomQuizzes()
  val quizResults: Flow<List<QuizSessionResultEntity>> = dao.getAllResults()
  val compoundMastery: Flow<List<CompoundMasteryEntity>> = dao.getAllMastery()

  suspend fun getQuestionsForQuiz(quizId: Long): List<TriviaQuestion> {
    val entities = dao.getQuestionsForQuizSync(quizId)
    return entities.map { entity ->
      TriviaQuestion(
        id = "custom_${entity.id}",
        compoundName = entity.compoundName,
        formula = entity.formula,
        type = try { CompoundType.valueOf(entity.type) } catch (e: Exception) { CompoundType.ORGANIC },
        difficulty = try { DifficultyLevel.valueOf(entity.difficulty) } catch (e: Exception) { DifficultyLevel.INTERMEDIATE },
        questionText = entity.questionText,
        options = entity.optionsJoined.split("|||"),
        correctIndex = entity.correctIndex,
        explanation = entity.explanation,
        chemicalFact = entity.chemicalFact,
        isCustom = true
      )
    }
  }

  suspend fun createCustomQuiz(
    title: String,
    description: String,
    category: CompoundType,
    difficulty: DifficultyLevel,
    questions: List<TriviaQuestion>
  ): Long {
    val quiz = CustomQuizEntity(
      title = title,
      description = description,
      category = category.name,
      difficulty = difficulty.name,
      questionCount = questions.size
    )

    val questionEntities = questions.map { q ->
      CustomQuestionEntity(
        quizId = 0, // Will be set by DAO transaction
        compoundName = q.compoundName,
        formula = q.formula,
        type = q.type.name,
        difficulty = q.difficulty.name,
        questionText = q.questionText,
        optionsJoined = q.options.joinToString("|||"),
        correctIndex = q.correctIndex,
        explanation = q.explanation,
        chemicalFact = q.chemicalFact
      )
    }

    return dao.insertFullCustomQuiz(quiz, questionEntities)
  }

  suspend fun deleteCustomQuiz(quizId: Long) {
    dao.deleteQuestionsForQuiz(quizId)
    dao.deleteCustomQuiz(quizId)
  }

  suspend fun recordQuizResult(
    title: String,
    category: CompoundType,
    difficulty: DifficultyLevel,
    score: Int,
    total: Int,
    xpEarned: Int,
    isDuel: Boolean = false,
    duelOpponent: String? = null,
    wonDuel: Boolean? = null
  ) {
    val result = QuizSessionResultEntity(
      quizTitle = title,
      category = category.name,
      difficulty = difficulty.name,
      score = score,
      totalQuestions = total,
      xpEarned = xpEarned,
      isDuel = isDuel,
      duelOpponent = duelOpponent,
      wonDuel = wonDuel
    )
    dao.insertResult(result)
  }

  suspend fun recordQuestionAnswer(
    formula: String,
    commonName: String,
    type: CompoundType,
    isCorrect: Boolean
  ) {
    val existing = dao.getMastery(formula)
    val updated = if (existing != null) {
      existing.copy(
        correctCount = if (isCorrect) existing.correctCount + 1 else existing.correctCount,
        incorrectCount = if (!isCorrect) existing.incorrectCount + 1 else existing.incorrectCount,
        lastReviewed = System.currentTimeMillis()
      )
    } else {
      CompoundMasteryEntity(
        formula = formula,
        commonName = commonName,
        type = type.name,
        correctCount = if (isCorrect) 1 else 0,
        incorrectCount = if (!isCorrect) 1 else 0,
        lastReviewed = System.currentTimeMillis()
      )
    }
    dao.insertOrUpdateMastery(updated)
  }

  suspend fun seedInitialDataIfEmpty() {
    // Check if we should insert a showcase custom quiz so user can test immediately
    // If no quizzes exist, populate one "Examen Parcial de Química Orgánica"
    // DAO check inside repository
  }

  // Pre-configured simulated global chemistry students leaderboard
  fun getGlobalLeaderboard(userXp: Int): List<LeaderboardUser> {
    val baseStudents = listOf(
      LeaderboardUser(1, "Elena Rostova", "Maestra de Síntesis", "Univ. Heidelberg", 4250, "94%", "Diamante Químico", "⚛️"),
      LeaderboardUser(2, "Kenji Takahashi", "Alquimista Mayor", "Univ. de Tokio", 3980, "91%", "Diamante Químico", "🧪"),
      LeaderboardUser(3, "Sofia Valenzuela", "Doctora en Polímeros", "UNAM Química", 3740, "88%", "Platino I", "🧬"),
      LeaderboardUser(4, "Mateo Bianchi", "Especialista en Iones", "Politecnico di Milano", 3520, "86%", "Platino II", "⚡"),
      LeaderboardUser(5, "Amara Diallo", "Química Computacional", "Sorbonne Université", 3310, "84%", "Platino III", "🔬"),
      LeaderboardUser(6, "Lucas Schmidt", "Analista de Redox", "ETH Zürich", 3100, "82%", "Oro I", "🔥"),
      LeaderboardUser(7, "Camila Morales", "Investigadora Bioquímica", "Univ. de Chile", 2890, "80%", "Oro II", "🌿"),
      LeaderboardUser(8, "David Chen", "Mago de Coordinación", "National Univ. Singapore", 2640, "78%", "Oro III", "💎"),
      LeaderboardUser(9, "Valeria Gomez", "Experta en Enlaces", "Univ. de Buenos Aires", 2420, "75%", "Plata I", "⚖️"),
      LeaderboardUser(10, "Alex Rivera", "Novicio del Matraz", "Instituto Politécnico", 2180, "72%", "Plata II", "🧤")
    )

    // Calculate user rank relative to global students
    val userEntry = LeaderboardUser(
      rank = 0, // Will be computed
      username = "Tú (Estudiante)",
      title = when {
        userXp >= 4000 -> "Diamante Alquimista"
        userXp >= 3000 -> "Maestro de Reacciones"
        userXp >= 1800 -> "Analista de Laboratorio"
        userXp >= 800 -> "Investigador Químico"
        else -> "Aprendiz de Química"
      },
      institution = "QuizMaster Academy",
      xp = userXp,
      winRate = if (userXp > 0) "85%" else "0%",
      league = when {
        userXp >= 3500 -> "Diamante Químico"
        userXp >= 2500 -> "Platino"
        userXp >= 1200 -> "Oro"
        userXp >= 500 -> "Plata"
        else -> "Bronce"
      },
      avatarSymbol = "🧑‍🔬",
      isCurrentUser = true
    )

    val combined = (baseStudents + userEntry).sortedByDescending { it.xp }
    return combined.mapIndexed { index, item -> item.copy(rank = index + 1) }
  }
}
