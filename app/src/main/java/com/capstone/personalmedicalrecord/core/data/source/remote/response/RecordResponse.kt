package com.capstone.personalmedicalrecord.core.data.source.remote.response

data class RecordResponse(
    var id: String = "",
    var date: String = "",
    var haematocrit: Double = 0.0,
    var haemoglobin: Double = 0.0,
    var erythrocyte: Double = 0.0,
    var leucocyte: Double = 0.0,
    var thrombocyte: Int = 0,
    var mch: Double = 0.0,
    var mchc: Double = 0.0,
    var mcv: Double = 0.0,
    var idPatient: String = "",
)