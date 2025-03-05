package com.example.mobile_hw_1.customview.custom_circle_journal

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.compose.ui.graphics.Color
import com.example.mobile_hw_1.R
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class CustomCircleJournal @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr){

    private data class EmoteData(var gradient: SweepGradient, var count: Int)

    private var emoteMap : MutableMap<String, EmoteData> = mutableMapOf()

    private var fullCount = 0
    private var maxCount = 3

    private var emoteData = listOf<EmoteData>()

    private var mainRadius = toPx(100)

    private var paintRadius = toPx(10)

    private var paintSizeShift = 12f

    private lateinit var circleRect : RectF

    private var size = -1

    private val bgColor = context.resources.getColor(R.color.circle_bg_color)
    private val gradientMainColor = context.resources.getColor(R.color.circle_grid_arrow_inactive_bg)
    private val colorArray = intArrayOf(
        gradientMainColor,
        gradientMainColor,
        bgColor,
        gradientMainColor)
    private val colorPartArray = floatArrayOf(45f / 360, 90f / 360f, 270f / 360f, 1f)

    private var animationAngle: Float = 0f
    private val trailCircleCount = 80
    private var sweepGradient = SweepGradient(
        width / 2f,
        height / 2f,
        colorArray,
        colorPartArray
    )

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
    }
    private var gradientPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = bgColor
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
        shader = sweepGradient
    }

    private var emotePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
    }

    private val valueAnimator = ValueAnimator.ofFloat(0f, 360f).apply {
        duration = 3000
        repeatCount = ValueAnimator.INFINITE
        interpolator = LinearInterpolator()
        addUpdateListener { animation ->
            animationAngle = animation.animatedValue as Float
            invalidate()
        }
    }

    private fun toPx(dp: Int): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        context.resources.displayMetrics
    )

    fun setEmoteData(dataList: List<Pair<String, Pair<IntArray, FloatArray>>>, newMax: Int = -1){
        fullCount = dataList.size
        maxCount = if (newMax == -1) dataList.size else newMax
        emoteMap.clear()
        if (fullCount > 0){
            dataList.forEach { emote ->
                if (emote.first in emoteMap){
                    emoteMap[emote.first]!!.count += 1
                }
                else{
                    emoteMap.put(emote.first, EmoteData(
                        SweepGradient(
                            size / 2f, size / 2f,
                            emote.second.first,
                            emote.second.second
                        ),
                        1))
                }
            }
            valueAnimator.cancel()
            invalidate()
        }
        else{
            startAnimation()
        }
    }

    fun startAnimation(){
        valueAnimator.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //Draw circle background
        paint.color = bgColor
        canvas.drawCircle(size / 2f, size / 2f, mainRadius, paint)

        //Draw gradient on empty circle if there is no emotions provided

        if (fullCount == 0){
            canvas.save()
            canvas.rotate(animationAngle, size / 2f, size / 2f)
            //canvas.drawCircle(size / 2f, size / 2f, mainRadius, paint)
            for (i in 0 until trailCircleCount){
                val x = width / 2f + cos(Math.PI / 2 - (Math.PI * i / trailCircleCount)).toFloat() * mainRadius
                val y = height / 2f + sin(Math.PI / 2 - (Math.PI * i / trailCircleCount)).toFloat() * mainRadius
                canvas.drawPoint(x, y, gradientPaint)
            }
            canvas.restore()
        }
        else{
            var startAngle = 270f
            for (i in emoteMap.keys){
                emotePaint.shader = emoteMap[i]!!.gradient
                val swipeAngle = 360f * (emoteMap[i]!!.count / maxCount.toFloat())
                canvas.drawArc(circleRect, startAngle, swipeAngle - paintSizeShift, false, emotePaint)
                startAngle += swipeAngle
            }
        }


        //canvas.drawCircle(size / 2f, size / 2f, mainRadius, paint)
        //canvas.drawArc(circleRect, 360f * (gradientStateOffset), 360f * (gradientStateOffset) + gradientArchAngleStep, false, paint)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        size = min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec))
        mainRadius = size / 2.2f
        paintRadius = mainRadius * 2f / 10f

        paint.strokeWidth = paintRadius
        gradientPaint.strokeWidth = paintRadius
        emotePaint.strokeWidth = paintRadius

        circleRect = RectF(
            size / 2f - (mainRadius),
            size / 2f - (mainRadius),
            size / 2f + (mainRadius),
            size / 2f + (mainRadius)
            )
        gradientPaint.shader = SweepGradient(
                size / 2f,
                size / 2f,
                    colorArray,
                    colorPartArray
        )

        setMeasuredDimension(size, size)
    }
}