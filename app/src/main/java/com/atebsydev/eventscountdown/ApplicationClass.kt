package com.atebsydev.eventscountdown

import android.app.Application
import com.atebsydev.eventscountdown.ui.data.EventRepository
import com.atebsydev.eventscountdown.database.AppDatabase
import com.atebsydev.eventscountdown.database.RoomEventDataSource

class ApplicationClass : Application() {

    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }

    val eventRepository: EventRepository
        get() {
            val roomEventDataSource = RoomEventDataSource(database.eventDao())
            return EventRepository(roomEventDataSource)
        }
}