package com.capstone.personalmedicalrecord.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Record(
    val id: Int = 0,
    val datetime: String,
    val description: String,
    val place: String,
    var idPatient: Int
) : Parcelable