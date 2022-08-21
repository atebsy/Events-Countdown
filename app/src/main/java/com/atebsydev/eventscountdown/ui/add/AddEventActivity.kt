package com.atebsydev.eventscountdown.ui.add

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.text.format.DateFormat.is24HourFormat
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.atebsydev.eventscountdown.MainActivity
import com.atebsydev.eventscountdown.databinding.ActivityAddEventBinding
import com.atebsydev.eventscountdown.util.afterTextChanged
import com.atebsydev.eventscountdown.util.getViewModelFactory
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CalendarConstraints.DateValidator
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*


class AddEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEventBinding
    private lateinit var addEventViewModel: AddEventViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addEventViewModel =
            ViewModelProvider(this, getViewModelFactory()).get(AddEventViewModel::class.java)
        binding = ActivityAddEventBinding.inflate(layoutInflater)
        setContentView(binding.root)


        addEventViewModel.eventFormState.observe(this, Observer {
            val eventFormState = it ?: return@Observer

            binding.btnSave.isEnabled = eventFormState.isDataValid

            if (eventFormState.titleError != null) {
                binding.edtEventTitle.error = getString(eventFormState.titleError)
            }

            if (eventFormState.dateError != null) {
                binding.edtEventDate.error = getString(eventFormState.dateError)
                binding.tvDateError.text = getString(eventFormState.dateError)
            } else {
                binding.edtEventDate.error = null
                binding.tvDateError.text = ""
            }

            if (eventFormState.timeError != null) {
                binding.edtEventTime.error = getString(eventFormState.timeError)
                binding.tvTimeError.text = getString(eventFormState.timeError)
            } else {
                binding.edtEventTime.error = null
                binding.tvTimeError.text = ""
            }
        })

        addEventViewModel.addEventResult.observe(this, Observer {
            var message = "Event successfully added"
            if (addEventViewModel.addEventResult.value == AddEventResult.ERROR) {
                message = "Event could not be aded"
            }
            clearForm()
            MaterialAlertDialogBuilder(this@AddEventActivity)
                .setMessage(message)
                .setPositiveButton("Ok") { dialogInterface, i ->

                }.show()
        })

        binding.edtEventTitle.afterTextChanged {
            addEventViewModel.addEventFormDataChanged(
                binding.edtEventTitle.text.toString(),
                binding.edtEventDate.text.toString(),
                binding.edtEventTime.text.toString()
            )
        }

        binding.edtEventDate.afterTextChanged {
            addEventViewModel.addEventFormDataChanged(
                binding.edtEventTitle.text.toString(),
                binding.edtEventDate.text.toString(),
                binding.edtEventTime.text.toString()
            )
        }

        binding.edtEventTime.afterTextChanged {
            addEventViewModel.addEventFormDataChanged(
                binding.edtEventTitle.text.toString(),
                binding.edtEventDate.text.toString(),
                binding.edtEventTime.text.toString()
            )
        }

        binding.edtEventDate.setOnClickListener {
            val dateValidator: DateValidator = DateValidatorPointForward.now()
            val constraintsBuilder = CalendarConstraints.Builder()
            constraintsBuilder.setValidator(dateValidator)
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Event date")
                    .setCalendarConstraints(constraintsBuilder.build())
                    .build()

            datePicker.addOnPositiveButtonClickListener {
                val dateString: String = DateFormat.format("dd/MM/yyyy", Date(it)).toString()
                binding.edtEventDate.setText(dateString)
            }
            datePicker.show(supportFragmentManager, "DatePickerDialog")
        }

        binding.edtEventTime.setOnClickListener {
            val isSystem24Hour = is24HourFormat(this)
            val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
            val timePicker = MaterialTimePicker.Builder()
                .setTitleText("Event time")
                .setTimeFormat(clockFormat)
                .build()
            timePicker.addOnPositiveButtonClickListener {
                val time = addEventViewModel.formatTime(timePicker.hour, timePicker.minute)
                binding.edtEventTime.setText(time)
            }
            timePicker.show(supportFragmentManager, "TimePickerDialog")
        }

        binding.btnSave.setOnClickListener {
            addEventViewModel.addNewEvent(
                binding.edtEventTitle.text.toString(),
                binding.edtEventDate.text.toString(),
                binding.edtEventTime.text.toString(),
                binding.edtEventNotes.text.toString()
            )
        }
    }

    private fun clearForm(){
        binding.edtEventTitle.setText("")
        binding.edtEventDate.setText("")
        binding.edtEventTime.setText("")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@AddEventActivity, MainActivity::class.java))
    }

}