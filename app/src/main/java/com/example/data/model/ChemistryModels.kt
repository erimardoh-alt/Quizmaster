package com.example.data.model

enum class CompoundType(val displayName: String, val badge: String) {
  ORGANIC("Orgánico", "C-H"),
  INORGANIC("Inorgánico", "Mineral / Iónico"),
  ALL("Todos", "Mix")
}

enum class DifficultyLevel(val levelNumber: Int, val title: String, val xpReward: Int) {
  BEGINNER(1, "Principiante", 25),
  INTERMEDIATE(2, "Intermedio", 50),
  ADVANCED(3, "Avanzado", 100),
  EXPERT(4, "Experto", 200)
}

data class ChemicalCompound(
  val formula: String,
  val commonName: String,
  val iupacName: String,
  val type: CompoundType,
  val functionalGroupOrFamily: String,
  val molarMass: String,
  val description: String,
  val hazardInfo: String = "Estable bajo condiciones normales"
)

data class TriviaQuestion(
  val id: String,
  val compoundName: String,
  val formula: String,
  val type: CompoundType,
  val difficulty: DifficultyLevel,
  val questionText: String,
  val options: List<String>,
  val correctIndex: Int,
  val explanation: String,
  val chemicalFact: String,
  val reactionEquation: String? = null,
  val hint: String = "",
  val isCustom: Boolean = false
)

enum class PowerUpType(val title: String, val description: String, val iconLabel: String) {
  FIFTY_FIFTY("50/50 Covalente", "Elimina 2 opciones incorrectas", "⚗️"),
  TIME_BOOST("Catalizador (+5s)", "Aumenta el tiempo de respuesta", "⏳"),
  SPECTROMETRY("Espectrometría", "Muestra pista molecular precisa", "🔬")
}

enum class DuelStatus {
  MATCHMAKING,
  COUNTDOWN,
  ROUND_ACTIVE,
  ROUND_RESULT,
  MATCH_FINISHED
}

data class DuelRoundState(
  val question: TriviaQuestion,
  val userChoice: Int? = null,
  val opponentChoice: Int? = null,
  val userTimeSeconds: Float = 0f,
  val opponentTimeSeconds: Float = 0f,
  val userScoreEarned: Int = 0,
  val opponentScoreEarned: Int = 0
)

data class LeaderboardUser(
  val rank: Int,
  val username: String,
  val title: String,
  val institution: String,
  val xp: Int,
  val winRate: String,
  val league: String,
  val avatarSymbol: String,
  val isCurrentUser: Boolean = false
)
