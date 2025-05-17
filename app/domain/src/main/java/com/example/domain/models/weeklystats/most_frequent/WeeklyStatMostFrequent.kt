package com.example.domain.models.weeklystats.most_frequent

import com.example.domain.models.EmotionGroup

data class WeeklyStatMostFrequent(
    val emotionsAndCount: List<Pair<EmotionGroup, Int>>
)