package com.capstone.personalmedicalrecord.core.data.source.remote.response

data class PatientResponse(
    var id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val dateBirth: String = "",
    val address: String = "",
    val gender: String = "",
    val bloodType: String = "",
    val picture: String = "",
    var term: Boolean = false,
)
