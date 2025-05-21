package com.example.domain.repositories

import com.example.domain.models.EmotionGroup
import com.example.domain.models.RecordModel
import java.time.LocalDate

interface RecordsRepository {
    suspend fun createRecord(
        emotion: EmotionGroup,
        tagsWhatDone: List<String>,
        tagsWithWho: List<String>,
        tagsWhereBeen: List<String>): Unit

    suspend fun getRecordList(
        fromDate: LocalDate,
        toDate: LocalDate): List<RecordModel>

    suspend fun getRecordForSpecDay(
        date: LocalDate
    ): List<RecordModel>
}