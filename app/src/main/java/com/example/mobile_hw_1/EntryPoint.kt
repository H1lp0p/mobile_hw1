package com.example.mobile_hw_1

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.mobile_hw_1.customview.custom_gradient.CustomGradientDrawable
import com.example.mobile_hw_1.databinding.EntryPointBinding


class EntryPoint : Fragment(R.layout.entry_point) {

    private var bindings: EntryPointBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindings = EntryPointBinding.bind(view)
        val layout = bindings!!.root

        MainActivity.getActivity().toggleNavBarVisibility(false)

        val animatedDrawable = CustomGradientDrawable(this.requireContext())
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

        view.findViewById<Button>(R.id.login).setOnClickListener{
            findNavController().navigate(EntryPointDirections.actionEntryPointToJournal())
        }
    }
}