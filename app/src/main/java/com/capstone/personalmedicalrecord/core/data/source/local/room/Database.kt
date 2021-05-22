package com.capstone.personalmedicalrecord.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.capstone.personalmedicalrecord.core.data.source.local.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {

    abstract fun noteDao(): NoteDao
}