package com.atebsydev.eventscountdown.ui.add

data class AddEventFormState(
    val titleError: Int? = null,
    val timeError: Int? = null,
    val dateError: Int? = null,
    val isDataValid: Boolean = false
)
