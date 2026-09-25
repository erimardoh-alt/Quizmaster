package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizMasterDao {

  @Query("SELECT * FROM custom_quizzes ORDER BY createdAt DESC")
  fun getAllCustomQuizzes(): Flow<List<CustomQuizEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCustomQuiz(quiz: CustomQuizEntity): Long

  @Query("DELETE FROM custom_quizzes WHERE id = :quizId")
  suspend fun deleteCustomQuiz(quizId: Long)

  @Query("SELECT * FROM custom_questions WHERE quizId = :quizId ORDER BY id ASC")
  fun getQuestionsForQuiz(quizId: Long): Flow<List<CustomQuestionEntity>>

  @Query("SELECT * FROM custom_questions WHERE quizId = :quizId ORDER BY id ASC")
  suspend fun getQuestionsForQuizSync(quizId: Long): List<CustomQuestionEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCustomQuestions(questions: List<CustomQuestionEntity>)

  @Query("DELETE FROM custom_questions WHERE quizId = :quizId")
  suspend fun deleteQuestionsForQuiz(quizId: Long)

  @Transaction
  suspend fun insertFullCustomQuiz(
    quiz: CustomQuizEntity,
    questions: List<CustomQuestionEntity>
  ): Long {
    val quizId = insertCustomQuiz(quiz)
    val questionsWithQuizId = questions.map { it.copy(quizId = quizId) }
    insertCustomQuestions(questionsWithQuizId)
    return quizId
  }

  @Query("SELECT * FROM quiz_results ORDER BY timestamp DESC")
  fun getAllResults(): Flow<List<QuizSessionResultEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertResult(result: QuizSessionResultEntity): Long

  @Query("SELECT * FROM compound_mastery ORDER BY lastReviewed DESC")
  fun getAllMastery(): Flow<List<CompoundMasteryEntity>>

  @Query("SELECT * FROM compound_mastery WHERE formula = :formula LIMIT 1")
  suspend fun getMastery(formula: String): CompoundMasteryEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOrUpdateMastery(mastery: CompoundMasteryEntity)
}
