package com.capstone.personalmedicalrecord.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.capstone.personalmedicalrecord.core.data.source.local.entity.NoteEntity
import com.capstone.personalmedicalrecord.core.data.source.local.entity.PatientEntity
import com.capstone.personalmedicalrecord.core.data.source.local.entity.RecordEntity
import com.capstone.personalmedicalrecord.core.data.source.local.entity.StaffEntity

@Database(
    entities = [NoteEntity::class, PatientEntity::class, RecordEntity::class, StaffEntity::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun patientDao(): PatientDao
    abstract fun recordDao(): RecordDao
    abstract fun staffDao(): StaffDao
}