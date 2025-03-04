package com.example.mobile_hw_1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StatsViewModel() : ViewModel() {

    private val _data = MutableLiveData<List<EmoteNote>>()
    val data: LiveData<List<EmoteNote>> = _data

    fun setData(list: List<EmoteNote>){
        _data.value = list
    }
}

enum class DayTime{
    earlyMorning,
    morning,
    midday,
    evening,
    lateEvening
}



data class EmoteNote(val emote: String, val date: String, val weekDay: Int, val dayTime: DayTime)