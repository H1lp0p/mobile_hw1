package com.example.mobile_hw_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.example.mobile_hw_1.databinding.SettingsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

interface bottomSheetDataTransferDate {
    fun onDataSelected(data: String)
}

class Settings : Fragment(R.layout.settings), bottomSheetDataTransferDate {
    override fun onDataSelected(data: String) {
        TODO("Not yet implemented")
    }

    private lateinit var bindings: SettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindings = SettingsBinding.bind(view)

        bindings.addNotif.setOnClickListener{
            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(20)
                .setMinute(0)
                .setTitleText(R.string.settings_bottom_sheet_notif)
                .setTheme(R.style.timePicker)
                .build()

            picker.show(childFragmentManager, "TimePicker")
        }
    }
}