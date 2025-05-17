package com.example.domain.models

import java.time.LocalDateTime

data class RecordModel (
    val id: Long,
    val date: LocalDateTime,
    val emotion: EmotionGroup,
    val tagsWhatDone: Array<String>,
    val tagsWithWho: Array<String>,
    val tagsWhereBeen: Array<String>
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RecordModel

        if (id != other.id) return false
        if (date != other.date) return false
        if (emotion != other.emotion) return false
        if (!tagsWhatDone.contentEquals(other.tagsWhatDone)) return false
        if (!tagsWithWho.contentEquals(other.tagsWithWho)) return false
        if (!tagsWhereBeen.contentEquals(other.tagsWhereBeen)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + emotion.hashCode()
        result = 31 * result + tagsWhatDone.contentHashCode()
        result = 31 * result + tagsWithWho.contentHashCode()
        result = 31 * result + tagsWhereBeen.contentHashCode()
        return result
    }

}