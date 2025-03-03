package com.example.mobile_hw_1.stats_fragments

data class DTO (
    val items: List<Emotion>,
    val dates: List<String>
)

data class Emotion(
    val emote: String,
    val count: Int
)