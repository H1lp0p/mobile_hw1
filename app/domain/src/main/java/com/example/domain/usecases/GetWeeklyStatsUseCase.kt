package com.example.domain.usecases

import com.example.domain.models.EmotionGroup
import com.example.domain.models.EmotionName
import com.example.domain.models.EmotionType
import com.example.domain.models.weeklystats.WeeklyStats
import com.example.domain.repositories.RecordsRepository
import com.example.domain.models.RecordModel
import com.example.domain.models.weeklystats.by_day.DayStat
import com.example.domain.models.weeklystats.by_day.WeeklyStatsByDay
import com.example.domain.models.weeklystats.category.Category
import com.example.domain.models.weeklystats.category.WeeklyStatCategory
import com.example.domain.models.weeklystats.during_day.DayPeriod
import com.example.domain.models.weeklystats.during_day.PeriodStats
import com.example.domain.models.weeklystats.during_day.WeeklyStatDuringDay
import com.example.domain.models.weeklystats.most_frequent.WeeklyStatMostFrequent
import java.time.DayOfWeek
import java.time.LocalDate

class GetWeeklyStatsUseCase(private val recordsRepository: RecordsRepository) {
    suspend fun getWeeklyStat(dateInWeek: LocalDate): WeeklyStats{

        //Normalizing date to the start of the current week
        var startDate = dateInWeek
        if (dateInWeek.dayOfWeek != DayOfWeek.MONDAY){
            startDate = startDate.minusDays(dateInWeek.dayOfWeek.value.toLong())
        }

        //end of the current week for repository
        val endDate = startDate.plusDays(7)

        val records = recordsRepository.getRecordList(fromDate = startDate, toDate = endDate)
        val totalCount = records.count()

        //for categories stats
        val typesCount : MutableMap<EmotionType, Int> = EmotionType.entries.associateWith { 0 }.toMutableMap()

        //for by day stats
        val week: MutableMap<DayOfWeek, MutableList<EmotionGroup>> = DayOfWeek.entries.associateWith { mutableListOf<EmotionGroup>() }.toMutableMap()

        //for most frequent
        val mostFrequent = mutableMapOf<EmotionGroup, Int>()

        //for during day
        val periods: Map<DayPeriod, MutableMap<EmotionType, Int>> = DayPeriod.entries.associateWith { EmotionType.entries.associateWith { 0 }.toMutableMap() }

        for (record: RecordModel in records){
            //for categories
            typesCount[record.emotion.type] = typesCount.getOrDefault(record.emotion.type, 0) + 1
            /*when(record.emotion){
                is EmotionGroup.Red -> typesCount[EmotionType.red] = typesCount[EmotionType.red]!! + 1
                is EmotionGroup.Blue -> typesCount[EmotionType.blue] = typesCount[EmotionType.blue]!! + 1
                is EmotionGroup.Green -> typesCount[EmotionType.green] = typesCount[EmotionType.green]!! + 1
                is EmotionGroup.Yellow -> typesCount[EmotionType.yellow] = typesCount[EmotionType.yellow]!! + 1
            }*/

            //for by day
            if (!week[record.date.dayOfWeek]!!.contains(record.emotion)){
                week[record.date.dayOfWeek]!!.add(record.emotion)
            }

            //for most frequent
            mostFrequent[record.emotion] = mostFrequent.getOrDefault(record.emotion, 0) + 1

            //for during day
            periods[DayPeriod.getPeriod(record.date)]?.get(record.emotion.type)?.plus(1)
        }
        //result WeeklyStatCategory
        val category = WeeklyStatCategory(
            categories = typesCount.map { it ->
                Category(
                    emotionType = it.key,
                    percent = it.value / totalCount * 100
                )
            }
        )

        val resultStatWeek = week.map {
            Pair(it.key, DayStat(
                emotions = it.value
            ))
        }

        //result WeeklyStatsByDay
        val byDay = WeeklyStatsByDay(
            week = resultStatWeek.toMap()
        )

        //result WeeklyStatsMostFrequent
        val frequency = WeeklyStatMostFrequent(
            emotionsAndCount = mostFrequent.map { Pair(it.key, it.value) }
        )

        //result WeeklyStatsDuringDay
        val duringDay = WeeklyStatDuringDay(
            periods = periods.map { Pair(it.key, PeriodStats(it.value)) }.toMap()
        )

        return WeeklyStats(
            category = category,
            duringDay = duringDay,
            byDay = byDay,
            mostFrequent = frequency
        )
    }
}