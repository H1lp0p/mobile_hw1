package com.example.domain.usecases

import com.example.domain.models.EmotionGroup
import com.example.domain.repositories.RecordsRepository

class CreateRecordUseCase (private val recordsRepository: RecordsRepository) {
    suspend fun createRecord(
        emotion: EmotionGroup,
        tagsWhatDone: List<String>,
        tagsWithWho: List<String>,
        tagsWhereBeen: List<String>
    ){
        return recordsRepository.createRecord(
            emotion = emotion,
            tagsWithWho = tagsWithWho,
            tagsWhatDone = tagsWhatDone,
            tagsWhereBeen = tagsWhereBeen,
        );
    }
}

