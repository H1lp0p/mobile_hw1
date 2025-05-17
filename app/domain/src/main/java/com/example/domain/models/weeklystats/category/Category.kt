package com.example.domain.models.weeklystats.category

import com.example.domain.models.EmotionType

data class Category(
    val emotionType: EmotionType,
    val percent: Int
)