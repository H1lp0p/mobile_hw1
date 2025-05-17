package com.example.mobile_hw_1.customview.custon_stat

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.mobile_hw_1.R
import com.google.android.material.imageview.ShapeableImageView

class CustomStatView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var isVertical = false

    private var progress = 1f
    private var progressPx = 0
    private var statDirection = 0

    private var wid = 0
    private var hig = 0

    init{
        LayoutInflater.from(context).inflate(R.layout.custom_stat, this, true)
        attrs?.let{
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomStatView, defStyleAttr, 0)

            progress = typedArray.getFloat(R.styleable.CustomStatView_percent, 1f)

            val bgDrawable = typedArray.getDrawable(R.styleable.CustomStatView_bar)

            var text = "0"
            typedArray.getString(R.styleable.CustomStatView_text)?.let {
                text = it
            }

            findViewById<ShapeableImageView>(R.id.bar).setBackgroundDrawable(bgDrawable)

            setText(text)
            showStat()

            typedArray.recycle()
        }
    }

    fun showStat(){
        val bar = findViewById<ShapeableImageView>(R.id.bar)

        val l = bar.layoutParams as ConstraintLayout.LayoutParams
        l.matchConstraintPercentWidth = progress

        l.height = ConstraintLayout.LayoutParams.MATCH_PARENT

        bar.layoutParams = l
        bar.forceLayout()
        bar.requestLayout()
        //Log.i("progress", "$progress ${(progress * wid).toInt()} ${bar.layoutParams.width} ${bar.width}")

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        wid = w
        hig = h
        showStat()
    }

    fun setText(newText: String){
        findViewById<TextView>(R.id.stat).text = newText
    }
}