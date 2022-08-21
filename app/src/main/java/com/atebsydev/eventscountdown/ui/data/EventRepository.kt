package com.atebsydev.eventscountdown.ui.data

import com.atebsydev.eventscountdown.core.IEventRepository
import com.atebsydev.eventscountdown.database.Event
import com.atebsydev.eventscountdown.database.RoomEventDataSource
import kotlinx.coroutines.flow.Flow

open class EventRepository(private val roomEventDataSource: RoomEventDataSource) :
    IEventRepository {

    override suspend fun addEvent(event: Event) {
        roomEventDataSource.addEvent(event)
    }

    override suspend fun deleteEventById(eventId: Int): Int {
        return roomEventDataSource.deleteEventById(eventId)
    }

    override suspend fun getPastEvents(): Flow<List<Event>> {
        return roomEventDataSource.getPastEvents()
    }

    override suspend fun getActiveEvents(): Flow<List<Event>> {

        return roomEventDataSource.getActiveEvents()
    }
}