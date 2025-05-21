package com.example.data.repository

import com.example.data.local.database.AppDb
import com.example.data.local.entity.RecordEntity
import com.example.data.mapper.RecordMapper.toModel
import com.example.domain.models.EmotionGroup
import com.example.domain.models.RecordModel
import com.example.domain.repositories.RecordsRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

class RecirdsRepositoryImpl(val appDb: AppDb): RecordsRepository {
    override suspend fun createRecord(
        emotion: EmotionGroup,
        tagsWhatDone: List<String>,
        tagsWithWho: List<String>,
        tagsWhereBeen: List<String>
    ) {
        val newRecord = RecordEntity(
            date = LocalDateTime.now(),
            emotionType = emotion.type,
            emotionName = emotion.name.emotionName,
            tagsWhatDone = tagsWhatDone,
            tagsWithWho = tagsWithWho,
            tagsWhereBeen = tagsWhereBeen,
        )

        appDb.recordsDao().insertRecord(newRecord)
    }

    override suspend fun getRecordList(fromDate: LocalDate, toDate: LocalDate): List<RecordModel> {
        val records = appDb.recordsDao().getRecordsInRange(
            fromDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli(),
            toDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
            )

        val resultRecords = records.map { it.toModel() }

        return resultRecords
    }

    override suspend fun getRecordForSpecDay(date: LocalDate): List<RecordModel> {
        val records = appDb.recordsDao().getRecordsInRange(
            date.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli(),
            date.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
        )

        val resultRecords = records.map { it.toModel() }

        return resultRecords
    }
}