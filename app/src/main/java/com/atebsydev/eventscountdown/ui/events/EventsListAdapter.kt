package com.atebsydev.eventscountdown.ui.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atebsydev.eventscountdown.BR
import com.atebsydev.eventscountdown.database.Event
import com.atebsydev.eventscountdown.databinding.EventListItemBinding

class EventsListAdapter : ListAdapter<Event, EventsListAdapter.EventViewHolder>(DiffCallback) {

    class EventViewHolder(private val binding: EventListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bin(event: Event) {
            binding.setVariable(BR.event, event)
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem.countDownString == newItem.countDownString
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {

        return EventViewHolder(
            EventListItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bin(getItem(position))
    }
}