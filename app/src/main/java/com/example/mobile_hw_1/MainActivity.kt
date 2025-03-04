package com.example.mobile_hw_1

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.ViewBindings
import com.example.mobile_hw_1.databinding.MainLayoutBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var bindings: MainLayoutBinding

    private lateinit var statsViewModel: StatsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        statsViewModel = ViewModelProvider(this)[StatsViewModel::class.java]

        statsViewModel.setData(listOf(
            EmoteNote(
                this.getString(R.string.feel_type_calmness),
                "сегодня, 23:30",
                1,
                DayTime.lateEvening
            ),
            EmoteNote(
                this.getString(R.string.feel_type_rage),
                "сегодня, 12:30",
                1,
                DayTime.midday
            ),
            EmoteNote(
                this.getString(R.string.feel_type_depression),
                "вчера, 21:30",
                7,
                DayTime.evening
            ),
            EmoteNote(
                this.getString(R.string.feel_type_confidence),
                "вчера, 10:30",
                7,
                DayTime.morning
            ),
        ))

        bindings = MainLayoutBinding.inflate(layoutInflater)

        activity = this

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val navView: BottomNavigationView = bindings.bottomNav

        navView.setupWithNavController(navController)

        setContentView(bindings.root)

    }

    fun toggleNavBarVisibility(visibility: Boolean = false){
        bindings.bottomNav.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    fun vibrate() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val effect = VibrationEffect.createOneShot(25, VibrationEffect.EFFECT_TICK)
                vibrator.vibrate(effect)
            } else {
                vibrator.vibrate(100)
            }
        } else {
            Toast.makeText(this, "No vibrator?", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private lateinit var activity: MainActivity

        fun getActivity(): MainActivity{
            return activity
        }
    }
}