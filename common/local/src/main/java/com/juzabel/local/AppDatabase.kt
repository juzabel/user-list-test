package com.juzabel.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.juzabel.local.dao.TestDao
import com.juzabel.local.models.Test

@Database(entities = [Test::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun testDao(): TestDao

    companion object {
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "test.db")
                .fallbackToDestructiveMigration().build()
        }
    }
}
