package com.atebsydev.eventscountdown.ui.events

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atebsydev.eventscountdown.core.IEventRepository
import com.atebsydev.eventscountdown.database.Event
import com.atebsydev.eventscountdown.util.Helper
import kotlinx.coroutines.launch

class EventsViewModel(private val eventRepository: IEventRepository) : ViewModel() {

    private var _tabIndex = MutableLiveData<Int>()
    val tabIndex: LiveData<Int> = _tabIndex

    private var _canRegisterBroadcast = MutableLiveData<Boolean>()
    val canRegisterBroadcast: LiveData<Boolean> = _canRegisterBroadcast

    private var _currentEvents = MutableLiveData<List<Event>>()
    val currentEvents: LiveData<List<Event>> = _currentEvents

    private var _pastEvents = MutableLiveData<List<Event>>()
    val pastEvents: LiveData<List<Event>> = _pastEvents


    fun setTabIndex(index: Int) {
        _tabIndex.value = index
    }

    init {
        Log.d("TAG", "init vm")
        _canRegisterBroadcast.value = true
    }

    fun setRegisterBroadcastValue() {
        _canRegisterBroadcast.value = false
    }

    fun getCurrentEvents() {
        viewModelScope.launch {
            eventRepository.getActiveEvents().collect {
                it.map { event ->
                    event.countDownString = Helper.formatDateToCountDownTimer(event.eventDate)
                }
                _currentEvents.value = it
            }
        }
    }

    fun getPastEvents() {
        viewModelScope.launch {
            try {
                eventRepository.getPastEvents().collect {
                    it.map { event ->
                        event.countDownString = Helper.formatDateToCountDownTimer(event.eventDate)
                    }
                    _pastEvents.value = it
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

}