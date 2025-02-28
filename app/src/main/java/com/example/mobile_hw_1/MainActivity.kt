package com.example.mobile_hw_1

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.ViewBindings
import com.example.mobile_hw_1.databinding.MainLayoutBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var bindings: MainLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

    companion object {
        private lateinit var activity: MainActivity

        fun getActivity(): MainActivity{
            return activity
        }
    }
}