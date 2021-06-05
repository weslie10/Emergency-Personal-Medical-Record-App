package com.capstone.personalmedicalrecord.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    var id: String = "",
    var datetime: String,
    var description: String,
    var from: String,
    var idPatient: String,
) : Parcelable