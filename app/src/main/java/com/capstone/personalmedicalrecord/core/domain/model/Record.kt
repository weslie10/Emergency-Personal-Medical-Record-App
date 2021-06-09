package com.capstone.personalmedicalrecord.core.domain.model

data class Record(
    var id: String = "",
    var date: String,
    var haematocrit: Double,
    var haemoglobin: Double,
    var erythrocyte: Double,
    var leucocyte: Double,
    var thrombocyte: Int,
    var mch: Double,
    var mchc: Double,
    var mcv: Double,
    var idPatient: String,
    var treatment: String,
)