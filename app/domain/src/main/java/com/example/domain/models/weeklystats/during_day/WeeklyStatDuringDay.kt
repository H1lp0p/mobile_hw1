package com.example.domain.models.weeklystats.during_day

data class WeeklyStatDuringDay(
    val periods: Map<DayPeriod, PeriodStats>
)