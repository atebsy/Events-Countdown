package com.atebsydev.eventscountdown.ui.events

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.atebsydev.eventscountdown.util.Helper
import java.time.OffsetDateTime


@BindingAdapter("date")
fun TextView.bindEventDate(eventDateTime: OffsetDateTime) {

    text = Helper.formatOffsetDateTime(eventDateTime)
}