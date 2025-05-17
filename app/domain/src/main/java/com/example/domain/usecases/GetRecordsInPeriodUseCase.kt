package com.example.domain.usecases

import com.example.domain.models.RecordModel
import com.example.domain.repositories.RecordsRepository
import java.time.LocalDate
import java.util.Date

class GetRecordsInPeriodUseCase(private val recordsRepository: RecordsRepository) {
    suspend fun getRecordsInPeriod(fromDate: LocalDate, toDate: LocalDate): List<RecordModel>
    {
        return recordsRepository.getRecordList(fromDate = fromDate, toDate = toDate)
    }
}