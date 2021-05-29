package com.capstone.personalmedicalrecord.core.domain.model

data class Patient(
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val dateBirth: String = "",
    val address: String = "",
    val gender: String = "",
    val bloodType: String = ""
)
