package com.example.mobile_hw_1

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_hw_1.customview.EmoteCard
import com.example.mobile_hw_1.customview.custom_gradient.CustomGradientDrawable
import com.example.mobile_hw_1.databinding.EmoteCardBinding
import com.example.mobile_hw_1.databinding.EntryPointBinding
import com.example.mobile_hw_1.databinding.JournalBinding

class Journal : Fragment(R.layout.journal) {

    private lateinit var bindings: JournalBinding

    private lateinit var testEmote_1 : List<Pair<String, Pair<IntArray, FloatArray>>>
    private lateinit var testEmote_2 : List<Pair<String, Pair<IntArray, FloatArray>>>

    private lateinit var testCards : MutableList<EmoteCardData>

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

        testCards = mutableListOf(
            EmoteCardData(
                requireContext().getString(R.string.feel_type_envy),
                "сегодня, 12:20"
            ),
            EmoteCardData(
                requireContext().getString(R.string.feel_type_depression),
                "сегодня, 8:21"
            ),
            EmoteCardData(
                requireContext().getString(R.string.feel_type_happiness),
                "вчера, 10:38"
            ),
            EmoteCardData(
                requireContext().getString(R.string.feel_type_calmness),
                "вчера, 23:01"
            ),
        )

        bindings = JournalBinding.bind(view)
        MainActivity.getActivity().toggleNavBarVisibility(true)

        bindings.addNoteBtn.setOnClickListener{
            findNavController().navigate(JournalDirections.actionJournalToCircleGrid())
        }

        bindings.testSwitchToCircle.setOnClickListener{
            if (testFlag == 0){
                bindings.circleJournal.setEmoteData(testEmote_1, 3)
                testFlag = 1
            }
            else if (testFlag == 1){
                bindings.circleJournal.setEmoteData(testEmote_2, 2)
                testFlag = 2
            }
            else{
                bindings.circleJournal.setEmoteData(listOf())
                testFlag = 0
            }
        }

        bindings.circleJournal.setEmoteData(testEmote_1)

        testCards.forEach { it ->
            val newCard = EmoteCard(requireContext())
            newCard.setEmote(it.emote)
            newCard.setDate(it.date)

            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.bottomMargin = 8.dpToPx(requireContext())
            newCard.layoutParams = layoutParams

            newCard.setOnClickListener {
                findNavController().navigate(JournalDirections.actionJournalToAddNote(
                    emote = newCard.getEmote(),
                    date = newCard.getDate()
                ))
            }

            bindings.cardContainer.addView(newCard)
        }
    }

    fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }
}

data class EmoteCardData(val emote: String, val date: String)

