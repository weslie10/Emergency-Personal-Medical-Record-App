package com.capstone.personalmedicalrecord.core.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteResponse(
    var id:  String,
    var datetime: String,
    var description: String,
    var from: String,
    var idPatient: String,
) : Parcelable