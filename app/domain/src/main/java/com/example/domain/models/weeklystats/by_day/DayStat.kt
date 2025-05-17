package com.example.domain.models.weeklystats.by_day

import com.example.domain.models.EmotionGroup

data class DayStat(
    val emotions: List<EmotionGroup>
)