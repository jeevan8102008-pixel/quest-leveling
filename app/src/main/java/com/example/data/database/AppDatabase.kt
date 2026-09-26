package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.dao.PlayerDao
import com.example.data.dao.QuestDao
import com.example.data.dao.WaterDao
import com.example.data.dao.WorkoutDao
import com.example.data.entity.DailyQuestEntity
import com.example.data.entity.PlayerEntity
import com.example.data.entity.WaterLogEntity
import com.example.data.entity.WorkoutLogEntity

@Database(
  entities = [
    PlayerEntity::class,
    DailyQuestEntity::class,
    WaterLogEntity::class,
    WorkoutLogEntity::class
  ],
  version = 1,
  exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun playerDao(): PlayerDao
  abstract fun questDao(): QuestDao
  abstract fun waterDao(): WaterDao
  abstract fun workoutDao(): WorkoutDao

  companion object {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          AppDatabase::class.java,
          "solo_fitness_quest.db"
        ).fallbackToDestructiveMigration().build()
        INSTANCE = instance
        instance
      }
    }
  }
}
