package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
  entities = [
    CustomQuizEntity::class,
    CustomQuestionEntity::class,
    QuizSessionResultEntity::class,
    CompoundMasteryEntity::class
  ],
  version = 1,
  exportSchema = false
)
abstract class QuizMasterDatabase : RoomDatabase() {
  abstract fun quizDao(): QuizMasterDao

  companion object {
    @Volatile
    private var INSTANCE: QuizMasterDatabase? = null

    fun getInstance(context: Context): QuizMasterDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          QuizMasterDatabase::class.java,
          "quizmaster_database"
        ).fallbackToDestructiveMigration().build()
        INSTANCE = instance
        instance
      }
    }
  }
}
