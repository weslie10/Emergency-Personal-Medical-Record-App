package com.capstone.personalmedicalrecord.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Record(
    var id: Int = 0,
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
) : Parcelable