package com.capstone.personalmedicalrecord.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    var id: Int = 0,
    var datetime: String,
    var description: String,
    var idPatient: Int
) : Parcelable