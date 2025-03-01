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

    private lateinit var testEmote_1 : List<Pair<String, Pair<IntArray, FloatArray>>>
    private lateinit var testEmote_2 : List<Pair<String, Pair<IntArray, FloatArray>>>

    private var testFlag = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        testEmote_1 = listOf(
            Pair(requireContext().getString(R.string.feel_type_confidence),
                Pair(
                    intArrayOf(
                        requireContext().getColor(R.color.feeling_good_gradient),
                        requireContext().getColor(R.color.feeling_good),
                        requireContext().getColor(R.color.feeling_good_gradient)
                    ),
                    floatArrayOf(
                        0f / 360f, 70f / 360f, 1f
                    )),
            ),
            Pair(requireContext().getString(R.string.feel_type_depression),
                Pair(
                    intArrayOf(
                        requireContext().getColor(R.color.feeling_angry_gradient),
                        requireContext().getColor(R.color.feeling_angry),
                        requireContext().getColor(R.color.feeling_angry_gradient)
                    ),
                    floatArrayOf(
                        0f / 360f, 70f / 360f, 1f
                    )),
            ),
        )
        testEmote_2 = listOf(
            Pair(requireContext().getString(R.string.feel_type_confidence),
                Pair(
                    intArrayOf(
                        requireContext().getColor(R.color.feeling_productive_gradient),
                        requireContext().getColor(R.color.feeling_productive),
                        requireContext().getColor(R.color.feeling_productive_gradient)
                    ),
                    floatArrayOf(
                        0f / 360f, 70f / 360f, 1f
                    )),
            )
        )

        bindings = JournalBinding.bind(view)
        MainActivity.getActivity().toggleNavBarVisibility(true)

        bindings.addNoteBtn.setOnClickListener{
            findNavController().navigate(JournalDirections.actionJournalToCircleGrid())
        }



        bindings.testSwitchToCircle.setOnClickListener{
            if (testFlag == 0){
                bindings.circleJournal.setEmoteData(testEmote_1)
                testFlag = 1
            }
            else if (testFlag == 1){
                bindings.circleJournal.setEmoteData(testEmote_2)
                testFlag = 2
            }
            else{
                bindings.circleJournal.setEmoteData(listOf())
                testFlag = 0
            }
        }

        bindings.circleJournal.setEmoteData(testEmote_1)
    }
}