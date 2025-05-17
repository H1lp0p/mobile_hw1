package com.example.domain.repositories

import com.example.domain.models.EmotionGroup
import com.example.domain.models.RecordModel
import java.time.LocalDate

interface RecordsRepository {
    suspend fun createRecord(
        emotion: EmotionGroup,
        tagsWhatDone: Array<String>,
        tagsWithWho: Array<String>,
        tagsWhereBeen: Array<String>): Unit

    suspend fun getRecordList(
        fromDate: LocalDate,
        toDate: LocalDate): List<RecordModel>
}