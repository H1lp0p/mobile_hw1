package com.example.mobile_hw_1.customview.custom_circle_grid

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.core.animation.addListener
import androidx.core.content.res.ResourcesCompat
import com.example.mobile_hw_1.CircleGrid
import com.example.mobile_hw_1.R
import java.lang.Integer.min
import kotlin.math.hypot

/**
 * Circle grid. Need to set emoteSetter from parent view to get access to selected item.
 */
class CircleGridView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {

    private data class Circle(val text: String, val color: Int, val point: PointF)

    private val paint = android.graphics.Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val emotionStrings = listOf(
        context.resources.getString(R.string.feel_type_rage),
        context.resources.getString(R.string.feel_type_envy),
        context.resources.getString(R.string.feel_type_burnout),
        context.resources.getString(R.string.feel_type_depression),
        context.resources.getString(R.string.feel_type_tension),
        context.resources.getString(R.string.feel_type_anxiety),
        context.resources.getString(R.string.feel_type_fatigue),
        context.resources.getString(R.string.feel_type_apathy),
        context.resources.getString(R.string.feel_type_excitement),
        context.resources.getString(R.string.feel_type_confidence),
        context.resources.getString(R.string.feel_type_calmness),
        context.resources.getString(R.string.feel_type_gratitude),
        context.resources.getString(R.string.feel_type_delight),
        context.resources.getString(R.string.feel_type_happiness),
        context.resources.getString(R.string.feel_type_satisfaction),
        context.resources.getString(R.string.feel_type_security)
    )

    private val colors = listOf(
        context.resources.getColor(R.color.feeling_angry),
        context.resources.getColor(R.color.feeling_productive),
        context.resources.getColor(R.color.feeling_bad),
        context.resources.getColor(R.color.feeling_good)
    )

    private val rows = 4
    private val cols = 4

    private val circles = mutableListOf<Circle>()

    private var viewWidth = -1
    private var viewHeight = -1

    lateinit var emoteSetter: (newEmote: String, newColor: Int) -> Unit

    private var selectedCircleIndex = -1
    private var baseRadius = toPx(56)
    private var bigRadius = toPx(76)
    private var gap = toPx(1)
    private var spacing = toPx(115)
    private var xOffset = 0f
    private var yOffset = 0f
    private var isDragging = false
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var text = ""
    private var counter = 0

    private val redEmotes = listOf(
        context.getString(R.string.feel_type_rage),
        context.getString(R.string.feel_type_envy),
        context.getString(R.string.feel_type_tension),
        context.getString(R.string.feel_type_anxiety),
    )
    private val yellowEmotes = listOf(
        context.getString(R.string.feel_type_excitement),
        context.getString(R.string.feel_type_delight),
        context.getString(R.string.feel_type_happiness),
        context.getString(R.string.feel_type_confidence),
    )
    private val blueEmotes = listOf(
        context.getString(R.string.feel_type_burnout),
        context.getString(R.string.feel_type_fatigue),
        context.getString(R.string.feel_type_depression),
        context.getString(R.string.feel_type_apathy),
    )
    private val greenEmotes = listOf(
        context.getString(R.string.feel_type_calmness),
        context.getString(R.string.feel_type_satisfaction),
        context.getString(R.string.feel_type_gratitude),
        context.getString(R.string.feel_type_security),
    )

    private fun toPx(dp: Int): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        context.resources.displayMetrics
    )

    private val expandedTextSize = toPx(16)
    private val BasictextSize = toPx(10)

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF000000.toInt()
        typeface = ResourcesCompat.getFont(context, R.font.gwen_trial_black)
        textSize = BasictextSize
        textAlign = Paint.Align.CENTER
    }

    init {

        for (i in 0 until emotionStrings.size){
            val x = i / rows
            val y = i % cols

            var color = 0xFFFFFF.toInt()

            when {
                redEmotes.contains(emotionStrings[i]) -> {
                    color = colors[0]
                }
                blueEmotes.contains(emotionStrings[i]) -> {
                    color = colors[2]
                }
                yellowEmotes.contains(emotionStrings[i]) -> {
                    color = colors[1]
                }
                greenEmotes.contains(emotionStrings[i]) -> {
                    color = colors[3]
                }
            }

            circles.add(Circle(emotionStrings[i], color, PointF(spacing * x, spacing * y)))
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        viewWidth = w
        viewHeight = h
        baseRadius = (w - gap * (rows - 1)) / (2 * rows)

        bigRadius = baseRadius + toPx(20)
        spacing = baseRadius * 2 + gap

        xOffset = w / 2f - 3 * baseRadius - gap * 2
        yOffset = h / 2f - 3 * baseRadius - gap * 2
        updateList();
    }

    private fun updateList() {
        for (i in emotionStrings.indices) {
            val x = i / rows
            val y = i % cols

            circles[i].point.x = spacing * x + xOffset
            circles[i].point.y = spacing * y + yOffset

            if (selectedCircleIndex != -1){
                if (i != selectedCircleIndex) {
                    if (i / rows == selectedCircleIndex / rows) {
                        if (i < selectedCircleIndex) {
                            circles[i].point.y -= bigRadius - baseRadius
                        } else {
                            circles[i].point.y += bigRadius - baseRadius
                        }
                    } else if (i % cols == selectedCircleIndex % cols) {
                        if (i < selectedCircleIndex) {
                            circles[i].point.x -= bigRadius - baseRadius
                        } else {
                            circles[i].point.x += bigRadius - baseRadius
                        }
                    }
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        circles.forEachIndexed { index, circle ->
            val radius = if (index == selectedCircleIndex) bigRadius else baseRadius
            textPaint.textSize = if (index == selectedCircleIndex) expandedTextSize else BasictextSize
            paint.color = circle.color
            canvas.drawCircle(circle.point.x, circle.point.y, radius, paint)
            canvas.drawText(circle.text, circle.point.x, circle.point.y + (textPaint.textSize / 3), textPaint)
        }
        if (selectedCircleIndex != -1){
            val circle = circles[selectedCircleIndex]
            paint.color = circle.color
            textPaint.textSize = expandedTextSize
            canvas.drawCircle(circle.point.x, circle.point.y, bigRadius, paint)
            canvas.drawText(circle.text, circle.point.x, circle.point.y + (textPaint.textSize / 3), textPaint)
        }
    }

    private var animationFlag = false

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val temp = circles.indexOfFirst { circle ->
                    hypot(
                        (event.x - circle.point.x).toDouble(),
                        (event.y - circle.point.y).toDouble()
                    ) <= baseRadius
                }

                if (temp != -1){
                    setCircleSelected(temp)
                }
                else{
                    isDragging = true
                    lastTouchX = event.x
                    lastTouchY = event.y
                }
                updateList()
                invalidate()
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                if (isDragging) {
                    val dx = event.x - lastTouchX
                    val dy = event.y - lastTouchY

                    xOffset += dx
                    yOffset += dy

                    lastTouchX = event.x
                    lastTouchY = event.y

                    selectCenteredCircle()
                    updateList()
                    invalidate()
                    return true
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isDragging = false
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    fun setCircleSelected(circleInd: Int){
        if (circleInd != selectedCircleIndex){
            selectedCircleIndex = circleInd
            emoteSetter(circles[circleInd].text, circles[circleInd].color)
        }
    }

    private fun reset(){
        circles.forEachIndexed { index, circle ->
            if (index != selectedCircleIndex) {
                if (index / rows == selectedCircleIndex / rows) {
                    if (index < selectedCircleIndex) {
                        circle.point.y += bigRadius - baseRadius
                    } else {
                        circle.point.y -= bigRadius - baseRadius
                    }
                } else if (index % cols == selectedCircleIndex % cols) {
                    if (index < selectedCircleIndex) {
                        circle.point.x += bigRadius - baseRadius
                    } else {
                        circle.point.x -= bigRadius - baseRadius
                    }
                }
            }
        }
    }

    fun resizeAroundSelected(){
        updateList()
        circles.forEachIndexed { index, circle ->
            if (index != selectedCircleIndex) {
                if (index / rows == selectedCircleIndex / rows) {
                    if (index < selectedCircleIndex) {
                        circle.point.y -= bigRadius - baseRadius
                    } else {
                        circle.point.y += bigRadius - baseRadius
                    }
                } else if (index % cols == selectedCircleIndex % cols) {
                    if (index < selectedCircleIndex) {
                        circle.point.x -= bigRadius - baseRadius
                    } else {
                        circle.point.x += bigRadius - baseRadius
                    }
                }
            }
        }
    }

    fun selectCenteredCircle(){
        val centerX = viewWidth / 2
        val centerY = viewHeight / 2

        var minDist = Float.MAX_VALUE
        var closestInd = -1

        circles.forEachIndexed{index, circle ->
            val distance = hypot(
                (centerX - circle.point.x).toDouble(),
                (centerY - circle.point.y).toDouble()
            )
            if (distance < minDist) {
                minDist = distance.toFloat()
                closestInd = index
            }
        }

        if (closestInd != -1){
            setCircleSelected(closestInd)
        }
    }
}