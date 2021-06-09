package com.capstone.personalmedicalrecord.core.domain.model

data class Note(
    var id: String = "",
    var datetime: String,
    var description: String,
    var from: String,
    var idPatient: String,
)