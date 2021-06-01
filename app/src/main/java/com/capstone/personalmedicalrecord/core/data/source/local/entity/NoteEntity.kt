package com.capstone.personalmedicalrecord.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "datetime")
    var datetime: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "idPatient")
    var idPatient: Int
) : Parcelable