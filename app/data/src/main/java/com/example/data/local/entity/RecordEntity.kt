package com.example.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.models.EmotionType
import java.time.LocalDateTime

@Entity(tableName = "records")
data class RecordEntity (
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,

    @ColumnInfo(name = "date") val date: LocalDateTime,
    @ColumnInfo(name = "emotion_type") val emotionType: EmotionType,
    @ColumnInfo(name = "emotion_name") val emotionName: String,
    //TODO: type converter with some separator
    @ColumnInfo(name = "tags_what_done") val tagsWhatDone: List<String>,
    @ColumnInfo(name = "tags_with_who") val tagsWithWho: List<String>,
    @ColumnInfo(name = "tags_where_been") val tagsWhereBeen: List<String>,
)