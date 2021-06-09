package com.capstone.personalmedicalrecord.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "datetime")
    var datetime: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "from")
    var from: String,

    @ColumnInfo(name = "idPatient")
    var idPatient: String,
)