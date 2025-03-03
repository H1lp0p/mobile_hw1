package com.example.mobile_hw_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.mobile_hw_1.stats_fragments.DTO
import com.example.mobile_hw_1.stats_fragments.Emotion
import com.example.mobile_hw_1.stats_fragments.ViewPageAdapter
import java.util.Date

class Stats : Fragment(R.layout.stats) {

    private var recyclePageCount = 5
    private var startDate = 1

    private lateinit var viewPager: ViewPager2

    private val testDto = DTO(
        listOf(
        Emotion("депрессия", 1)),
        listOf("10-11 фев")
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.viewPager)
        val adapter = ViewPageAdapter(testDto,this)
        viewPager.adapter = adapter

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val dots = listOf(
                    view.findViewById<ImageView>(R.id.dot1),
                    view.findViewById<ImageView>(R.id.dot2),
                    view.findViewById<ImageView>(R.id.dot3),
                    view.findViewById<ImageView>(R.id.dot4)
                )

                dots.forEachIndexed{ index, dot ->
                    dot.setColorFilter(
                        if (index == position)
                            this@Stats.requireActivity().getColor(R.color.white)
                        else
                            this@Stats.requireActivity().getColor(R.color.circle_grid_arrow_inactive_bg)
                    )
                }
            }
        })

        val items = (0 until recyclePageCount).map { RecycleItem("${startDate + 6 * it} - ${startDate + 6 * it + 6} дек") }

        view.findViewById<RecyclerView>(R.id.recyclerView).adapter = RecycleAdapter(items)
    }
}

data class RecycleItem(val text: String)

class RecycleAdapter(private val items: List<RecycleItem>) : RecyclerView.Adapter<RecycleAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_text_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    private var selectedInd: Int = -1

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.text)

        fun bind(item: RecycleItem) {
            textView.text = item.text
        }
    }
}