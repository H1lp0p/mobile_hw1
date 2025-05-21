package com.example.data.mapper

import com.example.data.local.entity.RecordEntity
import com.example.domain.models.EmotionGroup
import com.example.domain.models.EmotionType
import com.example.domain.models.RecordModel
import java.time.LocalDateTime

object RecordMapper {

    fun RecordModel.toRoomEntity(): RecordEntity{
        return RecordEntity(
            date = this.date,
            emotionType = this.emotion.type,
            emotionName = this.emotion.name.emotionName,
            tagsWhatDone = this.tagsWhatDone,
            tagsWithWho = this.tagsWithWho,
            tagsWhereBeen = this.tagsWhereBeen,
        )
    }

    fun RecordEntity.toModel(): RecordModel {
        val emotion: EmotionGroup = when(this.emotionType){
            EmotionType.red -> EmotionGroup.Red(EmotionGroup.Red.Name.valueOf(this.emotionName))
            EmotionType.blue -> EmotionGroup.Blue(EmotionGroup.Blue.Name.valueOf(this.emotionName))
            EmotionType.green -> EmotionGroup.Green(EmotionGroup.Green.Name.valueOf(this.emotionName))
            EmotionType.yellow -> EmotionGroup.Yellow(EmotionGroup.Yellow.Name.valueOf(this.emotionName))
        }
        return RecordModel(
            id = this.id,
            date = this.date,
            emotion = emotion,
            tagsWhatDone = this.tagsWhatDone,
            tagsWithWho = this.tagsWithWho,
            tagsWhereBeen = this.tagsWhereBeen
        )
    }
}