package com.capstone.personalmedicalrecord.core.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class RecordResponse(
    var id: String,
    var date: String,
    var haematocrit: Double,
    var haemoglobin: Double,
    var erythrocyte: Double,
    var leucocyte: Double,
    var thrombocyte: Int,
    var mch: Double,
    var mchc: Double,
    var mcv: Double,
    var idPatient: Int
)