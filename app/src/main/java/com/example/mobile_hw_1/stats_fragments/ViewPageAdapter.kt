package com.example.mobile_hw_1.stats_fragments

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPageAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {

    private val pageCount = 4

    override fun getItemCount(): Int {
        return pageCount
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            0 -> FirstFragment()
            1 -> SecondFragment()
            2 -> ThirdFragment()
            3 -> FourthFragment()
            else -> FirstFragment()
        }

        return fragment
    }
}