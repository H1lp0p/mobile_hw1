package com.example.domain.models.weeklystats.by_day

import java.time.DayOfWeek

data class WeeklyStatsByDay(
    val week: Map<DayOfWeek, DayStat>
)