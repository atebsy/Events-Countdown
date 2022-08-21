package com.atebsydev.eventscountdown.util

import androidx.fragment.app.Fragment
import com.atebsydev.eventscountdown.ApplicationClass
import com.atebsydev.eventscountdown.CustomViewModelFactory

fun Fragment.getViewModelFactory(): CustomViewModelFactory {
 val eventRepository = (requireContext().applicationContext as ApplicationClass).eventRepository
    return CustomViewModelFactory(eventRepository,this)
}