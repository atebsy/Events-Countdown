package com.atebsydev.eventscountdown.util

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.atebsydev.eventscountdown.ApplicationClass
import com.atebsydev.eventscountdown.CustomViewModelFactory

fun AppCompatActivity.getViewModelFactory(): CustomViewModelFactory {
    val eventRepository = (this.applicationContext as ApplicationClass).eventRepository
    return CustomViewModelFactory(eventRepository,this)
}