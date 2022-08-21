package com.atebsydev.eventscountdown.ui.events

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.atebsydev.eventscountdown.databinding.FragmentEventsBinding
import com.atebsydev.eventscountdown.util.getViewModelFactory
import kotlinx.coroutines.launch


/**
 * A placeholder fragment containing a simple view.
 */
class EventsFragment : Fragment() {

    private val eventsViewModel: EventsViewModel by viewModels {
        getViewModelFactory()
    }
    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventsListAdapter: EventsListAdapter
    private var currentTabIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventsViewModel.apply {
            setTabIndex(arguments?.getInt(ARG_TAB_INDEX) ?: 0)
            if (canRegisterBroadcast.value == true && arguments?.getInt(ARG_TAB_INDEX) == 0) {
                requireContext().registerReceiver(
                    MinuteElapsedBroadcastReceiver(),
                    IntentFilter(Intent.ACTION_TIME_TICK)
                )
                setRegisterBroadcastValue()
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.eventsList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        (recyclerView?.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        eventsListAdapter = EventsListAdapter()

        recyclerView.adapter = eventsListAdapter

        eventsViewModel.currentEvents.observe(viewLifecycleOwner, Observer {
            lifecycle.coroutineScope.launch {
                eventsListAdapter.submitList(it)
            }
        })

        eventsViewModel.pastEvents.observe(viewLifecycleOwner, Observer {
            eventsListAdapter.submitList(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eventsViewModel.tabIndex.observe(viewLifecycleOwner, Observer {

            if (it == 0) {
                eventsViewModel.getCurrentEvents()
            } else {
                eventsViewModel.getPastEvents()
            }
        })

        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_TAB_INDEX = "tab_index"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(tabIndex: Int): EventsFragment {
            return EventsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_TAB_INDEX, tabIndex)
                    currentTabIndex = tabIndex
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class MinuteElapsedBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            Log.d("TAG", "Minutes elapsed")
            if (currentTabIndex == 0) eventsViewModel.getCurrentEvents() else eventsViewModel.getPastEvents()
        }

    }
}