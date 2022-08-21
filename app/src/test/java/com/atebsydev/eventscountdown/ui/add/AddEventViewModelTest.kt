package com.atebsydev.eventscountdown.ui.add

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.atebsydev.eventscountdown.R
import com.atebsydev.eventscountdown.core.IEventRepository
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized


@RunWith(Parameterized::class)
class AddEventViewModelTest(
    private val expected: AddEventFormState,
    private val eventTitle: String,
    private val eventDate: String,
    private val eventTime: String,
    private val scenario: String) {

    lateinit var eventRepository: IEventRepository
    lateinit var addEventViewModel: AddEventViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        eventRepository = mock()
        addEventViewModel = AddEventViewModel(eventRepository)
    }

    companion object {
        //TODO: add more scenarios
        @JvmStatic
        @Parameterized.Parameters(name = "addEventFormDataChanged: {4}")
        fun addEventFormDataChangedInputs() = listOf(
            arrayOf(
                AddEventFormState(R.string.invalid_event_title),
                "",
                "12/01/2023",
                "12:09 PM",
                "empty event title"
            ),
            arrayOf(
                AddEventFormState(R.string.invalid_event_title),
                " ",
                "12/01/2023",
                "12:09 PM",
                "blank event title"
            )
        )
    }


    @Test
    fun addEventFormDataChanged() {
        addEventViewModel.addEventFormDataChanged(eventTitle, eventDate, eventTime)

        Assert.assertEquals(expected, addEventViewModel.eventFormState.value)
    }

}