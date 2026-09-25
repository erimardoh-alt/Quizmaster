package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_quizzes")
data class CustomQuizEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val title: String,
  val description: String,
  val category: String, // "ORGANIC", "INORGANIC", "ALL"
  val difficulty: String, // "BEGINNER", "INTERMEDIATE", "ADVANCED", "EXPERT"
  val questionCount: Int,
  val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "custom_questions")
data class CustomQuestionEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val quizId: Long,
  val compoundName: String,
  val formula: String,
  val type: String,
  val difficulty: String,
  val questionText: String,
  val optionsJoined: String, // Pipe-separated options: opt1|||opt2|||opt3|||opt4
  val correctIndex: Int,
  val explanation: String,
  val chemicalFact: String
)

@Entity(tableName = "quiz_results")
data class QuizSessionResultEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val quizTitle: String,
  val category: String,
  val difficulty: String,
  val score: Int,
  val totalQuestions: Int,
  val xpEarned: Int,
  val isDuel: Boolean = false,
  val duelOpponent: String? = null,
  val wonDuel: Boolean? = null,
  val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "compound_mastery")
data class CompoundMasteryEntity(
  @PrimaryKey val formula: String,
  val commonName: String,
  val type: String, // "ORGANIC" or "INORGANIC"
  val correctCount: Int = 0,
  val incorrectCount: Int = 0,
  val lastReviewed: Long = System.currentTimeMillis()
) {
  val isMastered: Boolean
    get() = correctCount >= 2 && (correctCount.toFloat() / (correctCount + incorrectCount).coerceAtLeast(1)) >= 0.7f
}
