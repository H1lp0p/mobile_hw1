package com.example.mobile_hw_1

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.ui.text.font.Typeface
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.util.Consumer
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mobile_hw_1.databinding.CircleGridBinding

class CircleGrid : Fragment(R.layout.circle_grid) {

    private lateinit var bindings : CircleGridBinding

    private var emoteString: String? = null
    private var emoteColor: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainActivity.getActivity().toggleNavBarVisibility(false)
        bindings = CircleGridBinding.bind(view)
        bindings.circleGridView.emoteSetter = {newEmote: String, newColor: Int ->
            updateSelectedEmote(newEmote, newColor)
        }

        bindings.backBtn.setOnClickListener{
            findNavController().popBackStack()
        }

        bindings.arrowBtn.setOnClickListener {
            findNavController().navigate(CircleGridDirections.actionCircleGridToAddNote())
        }
        bindings.arrowBtn.isEnabled = false
    }

    fun updateSelectedEmote(newEmote: String, newColor: Int){
        emoteString = newEmote
        emoteColor = newColor

        MainActivity.getActivity().vibrate()

        val text = bindings.basicText
        text.text = Html.fromHtml("<span style='color: ${emoteColor};'>${emoteString}</span><br>Это какая то там эмоция")

        bindings.arrowBtn.setColorFilter(requireContext().resources.getColor(R.color.black))
        bindings.arrowBtn.background = requireContext().resources.getDrawable(R.drawable.button_bg_solid_white_rounded)
        bindings.arrowBtn.isEnabled = true
    }
}