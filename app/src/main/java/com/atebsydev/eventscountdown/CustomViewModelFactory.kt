package com.atebsydev.eventscountdown

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.atebsydev.eventscountdown.ui.data.EventRepository
import com.atebsydev.eventscountdown.ui.add.AddEventViewModel
import com.atebsydev.eventscountdown.ui.events.EventsViewModel

class CustomViewModelFactory(
    private val eventRepository: EventRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) :
    AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    /**
     * Creates a new instance of the given `Class`.
     *
     *
     *
     * @param key a key associated with the requested ViewModel
     * @param modelClass a `Class` whose instance is requested
     * @param handle a handle to saved state associated with the requested ViewModel
     * @param <T> The type parameter for the ViewModel.
     * @return a newly created ViewModels
    </T> */
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(EventsViewModel::class.java) -> EventsViewModel(eventRepository)
            isAssignableFrom(AddEventViewModel::class.java) -> AddEventViewModel(eventRepository)
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}