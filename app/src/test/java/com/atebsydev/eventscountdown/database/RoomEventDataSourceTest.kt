package com.atebsydev.eventscountdown.database


import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.spy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

@RunWith(AndroidJUnit4::class)
internal class RoomEventDataSourceTest {

    lateinit var appDatabase: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }


    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun addEvent() {
        val eventDao = spy(appDatabase.eventDao())
        val roomEventDataSource = RoomEventDataSource(eventDao)
        val zoneOffSet = OffsetDateTime.now(ZoneOffset.systemDefault()).offset
        val offsetDateTime = OffsetDateTime.of(
            LocalDateTime.parse(
                "12/01/2023 12:10 PM",
                DateTimeFormatter.ofPattern("dd/MM/yyyy h:mm a", Locale.ENGLISH)
            ), zoneOffSet
        )

        runTest {
            val expected = Event(offsetDateTime,"test event","test note")

            roomEventDataSource.addEvent(expected)
            val actual = roomEventDataSource.queryByTitle(expected.eventTitle)
            expected.eventId = 1

            Assert.assertEquals(expected,actual)
        }
    }
}