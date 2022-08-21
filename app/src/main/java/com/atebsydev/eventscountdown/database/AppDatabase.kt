package com.atebsydev.eventscountdown.database

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.atebsydev.eventscountdown.util.DateTimeConverter

@Database(entities = arrayOf(Event::class), version = 1)
@TypeConverters(DateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "events_database.db"
                    )
                        .build()
                INSTANCE = instance

                instance
            }
        }
    }
}