package com.capstone.personalmedicalrecord.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patient")
data class PatientEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = "",

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
    var picture: String,

    @ColumnInfo(name = "term")
    var term: Boolean,
)