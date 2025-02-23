package com.example.mobile_hw_1

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobile_hw_1.customview.custom_gradient.CustomGradientDrawable
import com.example.mobile_hw_1.ui.theme.Mobile_hw_1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entry_point)

        val layout = findViewById<LinearLayout>(R.id.root)

        val animatedDrawable = CustomGradientDrawable(this)
        layout.background = animatedDrawable

        val animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 10000
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener { animation ->
                val fraction = animation.animatedValue as Float
                animatedDrawable.updatePositions(fraction)
            }
            start()
        }
    }
}