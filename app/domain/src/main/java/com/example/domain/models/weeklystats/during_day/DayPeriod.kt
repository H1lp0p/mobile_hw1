package com.example.domain.models.weeklystats.during_day

import java.time.LocalDateTime
import java.time.LocalTime

enum class DayPeriod(val period: String) {
    EARLY_MORN("early morning"),
    MORN("morning"),
    NOON("noon"),
    EVENING("evening"),
    LATE_EVENING("late evening");

    companion object{

        val timePeriods = mapOf(
            EARLY_MORN to Pair(LocalTime.of(3, 0), LocalTime.of(8, 0)),
            MORN to Pair(LocalTime.of(8, 1), LocalTime.of(11, 0)),
            NOON to Pair(LocalTime.of(11, 1), LocalTime.of(16, 0)),
            EVENING to Pair(LocalTime.of(16, 1), LocalTime.of(20, 0)),
            LATE_EVENING to Pair(LocalTime.of(20, 1), LocalTime.of(23, 59)),
        )

        fun getPeriod(date: LocalDateTime) : DayPeriod = when{
            date.toLocalTime() >= timePeriods[EARLY_MORN]!!.first && date.toLocalTime() <= timePeriods[EARLY_MORN]!!.second -> EARLY_MORN
            date.toLocalTime() >= timePeriods[MORN]!!.first && date.toLocalTime() <= timePeriods[MORN]!!.second -> MORN
            date.toLocalTime() >= timePeriods[NOON]!!.first && date.toLocalTime() <= timePeriods[NOON]!!.second -> NOON
            date.toLocalTime() >= timePeriods[EVENING]!!.first && date.toLocalTime() <= timePeriods[EVENING]!!.second -> EVENING
            date.toLocalTime() >= timePeriods[LATE_EVENING]!!.first && date.toLocalTime() <= timePeriods[LATE_EVENING]!!.second -> LATE_EVENING
            else -> LATE_EVENING
        }
    }
}