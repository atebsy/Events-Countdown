package com.atebsydev.eventscountdown.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Query("SELECT * FROM event WHERE DATETIME(event_date) > DATETIME('now') ORDER BY event_date ASC")
    fun getActiveEvents(): Flow<List<Event>>

    @Query("SELECT * FROM event WHERE DATETIME(event_date) < DATETIME('now') ORDER BY event_date ASC")
    fun getPastEvents(): Flow<List<Event>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event)

    @Query("DELETE FROM event WHERE id = :eventId")
    suspend fun deleteEventById(eventId: Int): Int

    @Query("SELECT * FROM event WHERE event_title = :eventTitle")
    fun queryByTitle(eventTitle: String): Event
}