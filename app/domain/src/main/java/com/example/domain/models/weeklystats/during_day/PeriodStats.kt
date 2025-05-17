package com.example.domain.models.weeklystats.during_day

import com.example.domain.models.EmotionType

data class PeriodStats(
    val types: Map<EmotionType, Int>
)