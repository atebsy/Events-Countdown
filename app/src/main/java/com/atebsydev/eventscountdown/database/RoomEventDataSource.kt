package com.atebsydev.eventscountdown.database


import com.atebsydev.eventscountdown.core.IEventDataSource
import kotlinx.coroutines.flow.Flow

class RoomEventDataSource(private val eventDao: EventDao) : IEventDataSource {

    override suspend fun addEvent(event: Event) {
        return eventDao.insertEvent(event)
    }

    override suspend fun deleteEventById(eventId: Int): Int {
        return eventDao.deleteEventById(eventId)
    }

    override suspend fun getPastEvents(): Flow<List<Event>> {
        return eventDao.getPastEvents()
    }

    override suspend fun getActiveEvents(): Flow<List<Event>> {
        return eventDao.getActiveEvents()
    }

    override suspend fun queryByTitle(eventTitle: String): Event {
        return eventDao.queryByTitle(eventTitle)
    }
}