package com.atebsydev.eventscountdown.database

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "event")
data class Event(
    @NonNull @ColumnInfo(name = "event_date") val eventDate: OffsetDateTime,
    @NonNull @ColumnInfo(name = "event_title") val eventTitle: String,
    @Nullable @ColumnInfo(name = "event_desc") val eventDescription: String
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var eventId: Int = 0
    var countDownString: String = ""
}
