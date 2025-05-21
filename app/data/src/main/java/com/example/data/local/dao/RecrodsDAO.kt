package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.local.entity.RecordEntity
import java.time.LocalDate

@Dao
interface RecrodsDAO {
    @Insert
    fun insertRecord(record: RecordEntity)

    // getting records from datetime range (also use it for current day)
    @Query("""
        SELECT * FROM records 
        WHERE date BETWEEN :fromTimestamp AND :toTimestamp 
        ORDER BY date DESC
    """)
    suspend fun getRecordsInRange(fromTimestamp: Long, toTimestamp: Long): List<RecordEntity>
}