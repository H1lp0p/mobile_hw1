package com.example.mobile_hw_1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mobile_hw_1.databinding.AddNoteBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

interface bottomSheetDataTransfer {
    fun onDataSelected(data: String)
}

class AddNote: Fragment(R.layout.add_note), bottomSheetDataTransfer {

    private lateinit var bindings: AddNoteBinding

    private lateinit var inflater: LayoutInflater

    private var groupInd = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val args = AddNoteArgs.fromBundle(requireArguments())
        
        val emoteText = args.emote
        val dateText = args.date

        Log.i("transfer", emoteText)

        super.onViewCreated(view, savedInstanceState)
        MainActivity.getActivity().toggleNavBarVisibility(false)

        inflater = LayoutInflater.from(this.context)

        bindings = AddNoteBinding.bind(view)

        bindings.emoteCard.setEmote(emoteText)
        bindings.emoteCard.setDate(dateText)

        bindings.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        bindings.addChip1.setOnClickListener {
            groupInd = 1
            addInput(bindings.addChip1.parent!! as ChipGroup, bindings.addChip1)
        }

        bindings.addChip2.setOnClickListener {
            groupInd = 2
            addInput(bindings.addChip2.parent!! as ChipGroup, bindings.addChip2)
        }

        bindings.addChip3.setOnClickListener {
            groupInd = 3
            addInput(bindings.addChip3.parent!! as ChipGroup, bindings.addChip3)
        }

        bindings.save.setOnClickListener{
            findNavController().navigate(AddNoteDirections.actionAddNoteToJournal())
        }
    }

    private fun addInput(chipGroup: ChipGroup, addChip: Chip){
        AddBottomSheet().show(childFragmentManager, AddBottomSheet.TAG)


    }

    private fun updateChipList(str: String){
        Toast.makeText(this.context, str, Toast.LENGTH_SHORT).show()

        if (groupInd == 1){
            val newChip = inflater.inflate(R.layout.my_chip, bindings.chipGroup1, false) as Chip
            newChip.text = str
            bindings.chipGroup1.addView(newChip, 0)
        }
        else if (groupInd == 2){
            val newChip = inflater.inflate(R.layout.my_chip, bindings.chipGroup2, false) as Chip
            newChip.text = str
            bindings.chipGroup2.addView(newChip, 0)
        }
        else if (groupInd == 3){
            val newChip = inflater.inflate(R.layout.my_chip, bindings.chipGroup3, false) as Chip
            newChip.text = str
            bindings.chipGroup3.addView(newChip, 0)
        }
        else{
            Log.i("you dum", "groupInd is $groupInd")
        }
    }

    override fun onDataSelected(data: String) {
        if (data.length > 15){
            updateChipList(data.substring(0, 15) + "...")
        }
        else{
            updateChipList(data)
        }
        groupInd = -1
    }
}

class AddBottomSheet: BottomSheetDialogFragment() {

    private lateinit var root : View

    private lateinit var callback: bottomSheetDataTransfer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        root = inflater.inflate(R.layout.bottom_sheet_text, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, Bundle())
        callback = parentFragment as bottomSheetDataTransfer
        root.findViewById<ImageButton>(R.id.saveBtn).setOnClickListener {
            callback.onDataSelected(root.findViewById<EditText>(R.id.editText).text.toString())
            dismiss()
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}