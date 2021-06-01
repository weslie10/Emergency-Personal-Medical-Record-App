package com.capstone.personalmedicalrecord.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "record")
data class RecordEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "haematocrit")
    var haematocrit: Double,

    @ColumnInfo(name = "haemoglobin")
    var haemoglobin: Double,

    @ColumnInfo(name = "erythrocyte")
    var erythrocyte: Double,

    @ColumnInfo(name = "leucocyte")
    var leucocyte: Double,

    @ColumnInfo(name = "thrombocyte")
    var thrombocyte: Int,

    @ColumnInfo(name = "mch")
    var mch: Double,

    @ColumnInfo(name = "mchc")
    var mchc: Double,

    @ColumnInfo(name = "mcv")
    var mcv: Double,

    @ColumnInfo(name = "idPatient")
    var idPatient: Int
) : Parcelable

