package com.example.domain.models

sealed class EmotionGroup(val type: EmotionType){
    abstract val name: EmotionName
    data class Red(override val name: Name): EmotionGroup(EmotionType.red){
        enum class Name(override val emotionName: String) : EmotionName{
            RAGE("rage"),
            ENVY("envy"),
            TENSION("tension"),
            ANXIETY("anxiety")
        }
    }

    data class Blue(override val name: Name): EmotionGroup(EmotionType.blue){
        enum class Name(override val emotionName: String) : EmotionName{
            BURNOUT("burnout"),
            FATIGUE("fatigue"),
            DEPRESSION("depression"),
            APATHY("apathy")
        }
    }

    data class Green(override val name: Name): EmotionGroup(EmotionType.green){
        enum class Name(override val emotionName: String) : EmotionName{
            CALMNESS("calmness"),
            SATISFACTION("satisfaction"),
            GRATITUDE("gratitude"),
            SECURITY("security")
        }
    }

    data class Yellow(override val name: Name): EmotionGroup(EmotionType.yellow){
        enum class Name(override val emotionName: String) : EmotionName{
            EXCITEMENT("excitement"),
            DELIGHT("delight"),
            HAPPINESS("happiness"),
            CONFIDENCE("confidence")
        }
    }
}