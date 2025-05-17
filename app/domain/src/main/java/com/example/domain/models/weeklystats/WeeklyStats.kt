package com.example.domain.models.weeklystats

import com.example.domain.models.weeklystats.by_day.WeeklyStatsByDay
import com.example.domain.models.weeklystats.category.WeeklyStatCategory
import com.example.domain.models.weeklystats.during_day.WeeklyStatDuringDay
import com.example.domain.models.weeklystats.most_frequent.WeeklyStatMostFrequent

data class WeeklyStats(
    val category: WeeklyStatCategory,
    val duringDay: WeeklyStatDuringDay,
    val byDay: WeeklyStatsByDay,
    val mostFrequent: WeeklyStatMostFrequent
)