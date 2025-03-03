package com.example.mobile_hw_1.customview.custom_gradient

import android.content.Context
import android.graphics.*
import android.graphics.Paint
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.mobile_hw_1.R
import kotlin.math.cos
import kotlin.math.sin


/**
*   My cool custom gradient drawable for entry point
 */
class CustomGradientDrawable(
    context: Context
) : Drawable()
{

    private var paint = Paint()

    private val positions = mutableListOf<PointF>()

    private var  colors = listOf(
        intArrayOf(ContextCompat.getColor(context, R.color.entry_point_gradient_color2), Color.TRANSPARENT),
        intArrayOf(ContextCompat.getColor(context, R.color.entry_point_gradient_color1), Color.TRANSPARENT),
        intArrayOf(ContextCompat.getColor(context, R.color.entry_point_gradient_color4), Color.TRANSPARENT),
        intArrayOf(ContextCompat.getColor(context, R.color.entry_point_gradient_color3), Color.TRANSPARENT)
    )

    private val colorWhite = ContextCompat.getColor(context, R.color.white)

    private val gradients = mutableListOf<RadialGradient>(
        RadialGradient(
            1.0f,
            1.0f,
            0.3f,
            colors[0],
            null,
            Shader.TileMode.CLAMP
        ),
        RadialGradient(
            1.0f,
            1.0f,
            0.3f,
            colors[1],
            null,
            Shader.TileMode.CLAMP
        ),
        RadialGradient(
            1.0f,
            1.0f,
            0.3f,
            colors[2],
            null,
            Shader.TileMode.CLAMP
        ),
        RadialGradient(
            1.0f,
            1.0f,
            0.3f,
            colors[3],
            null,
            Shader.TileMode.CLAMP
        ),
    )

    init {
        paint.isAntiAlias = true
    }

    override fun draw(canvas: Canvas) {
        paint.color = colorWhite
        canvas.drawRect(bounds, paint)
        for (gradient in gradients) {
            paint.shader = gradient
            canvas.drawRect(bounds, paint)
        }
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        for (i in gradients.indices) {
            if (i == 0){
                positions.add(PointF(0.0f, 0.0f))
            }
            else if (i == 1){
                positions.add(PointF(1.0f, 0.0f))
            }
            else if (i == 2){
                positions.add(PointF(1.0f, 1.0f))
            }
            else if (i == 3){
                positions.add(PointF(0.0f, 1.0f))
            }
            gradients[i] = createGradient(colors[i], positions[i])
        }
    }

    private fun createGradient(colors: IntArray, position: PointF): RadialGradient {
        val radius = bounds.width() * 0.3f
        if (radius <= 0) {
            throw IllegalArgumentException("You dumass! Bounds width is ${bounds.width()}")
        }
        return RadialGradient(
            bounds.width() * position.x,
            bounds.height() * position.y,
            bounds.width().toFloat() * 1.5f,
            colors,
            null,
            Shader.TileMode.CLAMP
        )
    }

    fun updatePositions(fraction: Float) {
        for (i in positions.indices) {
            positions[i].x = 0.5f + (sin(fraction * Math.PI * 2 + (Math.PI / 2) * i).toFloat() * 0.7f)
            positions[i].y = 0.5f + (cos(fraction * Math.PI * 2 + (Math.PI / 2) * i).toFloat() * 0.7f)
            gradients[i] = createGradient(colors[i], positions[i])
        }
        invalidateSelf()
    }
}