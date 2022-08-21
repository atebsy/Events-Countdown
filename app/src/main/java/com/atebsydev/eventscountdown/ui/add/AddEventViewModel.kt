package com.atebsydev.eventscountdown.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atebsydev.eventscountdown.R
import com.atebsydev.eventscountdown.core.IEventRepository
import com.atebsydev.eventscountdown.database.Event
import kotlinx.coroutines.launch
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

class AddEventViewModel(private val eventRepository: IEventRepository) : ViewModel() {

    private var _eventFormState = MutableLiveData<AddEventFormState>()
    val eventFormState: LiveData<AddEventFormState> = _eventFormState

    private var _addEventResult = MutableLiveData<AddEventResult>()
    val addEventResult: LiveData<AddEventResult> = _addEventResult


    fun addNewEvent(eventTitle: String, eventDate: String, eventTime: String, eventNotes: String) {
        val zoneOffSet = OffsetDateTime.now(ZoneOffset.systemDefault()).offset
        val offsetDateTime = OffsetDateTime.of(
            LocalDateTime.parse(
                "$eventDate $eventTime",
                DateTimeFormatter.ofPattern("dd/MM/yyyy h:mm a", Locale.ENGLISH)
            ), zoneOffSet
        )
        viewModelScope.launch {
            try {
                eventRepository.addEvent(Event(offsetDateTime, eventTitle, eventNotes))
                _addEventResult.value = AddEventResult.SUCCESS
            } catch (exception: Exception) {
                _addEventResult.value = AddEventResult.ERROR
                exception.printStackTrace()
            }
        }
    }

    fun addEventFormDataChanged(eventTitle: String, eventDate: String, eventTime: String) {
        if (eventTitle.isBlank() || eventTitle.isEmpty()) {
            _eventFormState.value = AddEventFormState(titleError = R.string.invalid_event_title)
        } else if (eventDate.isNotEmpty()) {
            if (LocalDate.now()
                    .isAfter(LocalDate.parse(eventDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")))
            ) {
                _eventFormState.value = AddEventFormState(dateError = R.string.selected_date_error)
            } else if (eventTime.isNotEmpty() && eventTime.isNotBlank() && LocalDate.now()
                    .isEqual(LocalDate.parse(eventDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")))
            ) {
                if (isTimeValid(eventTime)) {
                    _eventFormState.value = AddEventFormState(isDataValid = true)
                } else {
                    _eventFormState.value =
                        AddEventFormState(timeError = R.string.time_must_be_greater_error)
                }
            } else {
                if (eventTime.isNotEmpty()) {
                    _eventFormState.value = AddEventFormState(isDataValid = true)
                }
            }
        }
    }

    private fun isTimeValid(eventTime: String): Boolean {
        //TODO: set pattern to match default locale, not just english time format
        if (LocalTime.now().isAfter(
                LocalTime.parse(
                    eventTime,
                    DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)
                )
            )
        )
            return false
        return true
    }

    fun formatTime(hour: Int, min: Int): String {
        //TODO: format according to default locale, not just english time format
        var format: String
        var hour = hour
        var formattedMinutes: String? = null
        var formattedHours: String? = null

        if (hour == 0) {
            hour += 12
            format = "AM"
        } else if (hour == 12) {
            format = "PM"
        } else if (hour > 12) {
            hour -= 12
            format = "PM"
        } else {
            format = "AM"
        }

        if (hour < 10) formattedHours = "0$hour" else formattedHours = hour.toString()
        if (min < 10) formattedMinutes = "0$min" else formattedMinutes = min.toString()

        return "$formattedHours:$formattedMinutes $format"
    }
}