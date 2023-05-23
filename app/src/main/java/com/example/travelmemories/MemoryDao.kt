package com.example.travelmemories

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MemoryDao {
    @Query("SELECT * FROM memory")
    suspend fun getAll(): List<MemoryEntity>

    @Query("SELECT * FROM memory WHERE name = :name")
    fun getMemoryByName(name: String): MemoryEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(memory: MemoryEntity)

    @Update
    fun update(memory: MemoryEntity)

    @Delete
    fun delete(memory: MemoryEntity)
}