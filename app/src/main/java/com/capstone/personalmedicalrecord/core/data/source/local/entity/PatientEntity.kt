package com.capstone.personalmedicalrecord.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "patient")
data class PatientEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "password")
    var password: String,

    @ColumnInfo(name = "phoneNumber")
    var phoneNumber: String,

    @ColumnInfo(name = "dateBirth")
    var dateBirth: String,

    @ColumnInfo(name = "address")
    var address: String,

    @ColumnInfo(name = "gender")
    var gender: String,

    @ColumnInfo(name = "bloodType")
    var bloodType: String,

    @ColumnInfo(name = "picture")
    var picture: String
) : Parcelable