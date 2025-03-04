package com.example.mobile_hw_1.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import com.example.mobile_hw_1.R

class EmoteCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private lateinit var body: ConstraintLayout
    private var emoteText: String = "Ничего"
    private var date: String = "сегодня, 4:30 (это placeholder, кста)"
    private lateinit var icon: ImageView

    private val redEmotes = listOf(
        context.getString(R.string.feel_type_rage),
        context.getString(R.string.feel_type_envy),
        context.getString(R.string.feel_type_tension),
        context.getString(R.string.feel_type_anxiety),
    )
    private val redEmoteIcon = context.getDrawable(R.drawable.soft_flower)
    private val redEmoteGradient = context.getDrawable(R.drawable.log_card_angerness)
    private val redEmoteTextColor = context.getColor(R.color.feeling_angry)

    private val yellowEmotes = listOf(
        context.getString(R.string.feel_type_excitement),
        context.getString(R.string.feel_type_delight),
        context.getString(R.string.feel_type_happiness),
        context.getString(R.string.feel_type_confidence),
    )
    private val yellowEmoteIcon = context.getDrawable(R.drawable.lightning_1)
    private val yellowEmoteGradient = context.getDrawable(R.drawable.log_card_productivity)
    private val yellowEmoteTextColor = context.getColor(R.color.feeling_productive)

    private val blueEmotes = listOf(
        context.getString(R.string.feel_type_burnout),
        context.getString(R.string.feel_type_fatigue),
        context.getString(R.string.feel_type_depression),
        context.getString(R.string.feel_type_apathy),
    )
    private val blueEmoteIcon = context.getDrawable(R.drawable.literally_me)
    private val blueEmoteGradient = context.getDrawable(R.drawable.log_card_sadness)
    private val blueEmoteTextColor = context.getColor(R.color.feeling_bad)

    private val greenEmotes = listOf(
        context.getString(R.string.feel_type_calmness),
        context.getString(R.string.feel_type_satisfaction),
        context.getString(R.string.feel_type_gratitude),
        context.getString(R.string.feel_type_security),
    )
    private val greenEmoteIcon = context.getDrawable(R.drawable.mithosis)
    private val greenEmoteGradient = context.getDrawable(R.drawable.log_card_happines)
    private val greenEmoteTextColor = context.getColor(R.color.feeling_good)

    init{
        LayoutInflater.from(context).inflate(R.layout.emote_card, this, true)
        body = findViewById(R.id.card_body)
        icon = findViewById(R.id.emote_icon)

        attrs?.let{
            val typedArray = context.obtainStyledAttributes(it, R.styleable.EmoteCard, defStyleAttr, 0)
            typedArray.getString(R.styleable.EmoteCard_emote)?.let {
                emoteText = it
            }
            typedArray.getString(R.styleable.EmoteCard_date)?.let{
                date = it
            }
            typedArray.recycle()
        }

        val emoteTextView = findViewById<TextView>(R.id.emote_card_feel)
        emoteTextView.text = emoteText
        findViewById<TextView>(R.id.date).text = date

        when {
            redEmotes.contains(emoteText) -> {
                body.background = redEmoteGradient
                emoteTextView.setTextColor(redEmoteTextColor)
                icon.setImageDrawable(redEmoteIcon)

            }
            blueEmotes.contains(emoteText) -> {
                body.background = blueEmoteGradient
                emoteTextView.setTextColor(blueEmoteTextColor)
                icon.setImageDrawable(blueEmoteIcon)
            }
            yellowEmotes.contains(emoteText) -> {
                body.background = yellowEmoteGradient
                emoteTextView.setTextColor(yellowEmoteTextColor)
                icon.setImageDrawable(yellowEmoteIcon)
            }
            greenEmotes.contains(emoteText) -> {
                body.background = greenEmoteGradient
                emoteTextView.setTextColor(greenEmoteTextColor)
                icon.setImageDrawable(greenEmoteIcon)
            }
            else -> {
            }
        }

    }

    fun setEmote(newEmote: String){
        val emoteTextView = findViewById<TextView>(R.id.emote_card_feel)
        emoteText = newEmote
        emoteTextView.text = newEmote
        when {
            redEmotes.contains(newEmote) -> {
                body.background = redEmoteGradient
                emoteTextView.setTextColor(redEmoteTextColor)
                icon.setImageDrawable(redEmoteIcon)

            }
            blueEmotes.contains(newEmote) -> {
                body.background = blueEmoteGradient
                emoteTextView.setTextColor(blueEmoteTextColor)
                icon.setImageDrawable(blueEmoteIcon)
            }
            yellowEmotes.contains(newEmote) -> {
                body.background = yellowEmoteGradient
                emoteTextView.setTextColor(yellowEmoteTextColor)
                icon.setImageDrawable(yellowEmoteIcon)
            }
            greenEmotes.contains(newEmote) -> {
                body.background = greenEmoteGradient
                emoteTextView.setTextColor(greenEmoteTextColor)
                icon.setImageDrawable(greenEmoteIcon)
            }
            else -> {
                emoteTextView.text = "Ничего"
                emoteText = "Ничего"
                emoteTextView.setTextColor(context.getColor(R.color.white))
                body.background = context.getDrawable(R.drawable.logcard_empty_emote)
                icon.setImageDrawable(context.getDrawable(R.drawable.emote_placeholder))
            }
        }
    }

    fun setDate(newDate: String){
        date = newDate
        findViewById<TextView>(R.id.date).text = newDate
    }

    fun getEmote() : String = emoteText

    fun getDate() : String = date

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            child.layout(0, 0, child.measuredWidth, child.measuredHeight)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        measureChildren(widthMeasureSpec, heightMeasureSpec)

        val child = getChildAt(0)
        val width = child.measuredWidth
        val height = child.measuredHeight

        setMeasuredDimension(width, height)
    }
}