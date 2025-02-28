package com.example.mobile_hw_1

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mobile_hw_1.customview.custom_gradient.CustomGradientDrawable
import com.example.mobile_hw_1.databinding.EntryPointBinding
import com.example.mobile_hw_1.databinding.JournalBinding

class Journal : Fragment(R.layout.journal) {

    private lateinit var bindings: JournalBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindings = JournalBinding.bind(view)
        MainActivity.getActivity().toggleNavBarVisibility(true)

        bindings.addNoteBtn.setOnClickListener{
            findNavController().navigate(JournalDirections.actionJournalToCircleGrid())
        }
    }
}