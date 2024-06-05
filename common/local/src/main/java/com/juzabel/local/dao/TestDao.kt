package com.juzabel.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.juzabel.local.models.Test
import kotlinx.coroutines.flow.Flow

@Dao
interface TestDao {
    @Insert
    suspend fun insert(test: Test)

    @Query("Select * from Test")
    fun gelAll(): Flow<List<Test>>

    @Update
    suspend fun update(test: Test)

    @Delete
    suspend fun deleter(test: Test)
}
