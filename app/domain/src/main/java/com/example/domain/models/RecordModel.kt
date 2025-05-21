package com.example.domain.models

import java.time.LocalDateTime

data class RecordModel (
    val id: Long,
    val date: LocalDateTime,
    val emotion: EmotionGroup,
    val tagsWhatDone: List<String>,
    val tagsWithWho: List<String>,
    val tagsWhereBeen: List<String>
)