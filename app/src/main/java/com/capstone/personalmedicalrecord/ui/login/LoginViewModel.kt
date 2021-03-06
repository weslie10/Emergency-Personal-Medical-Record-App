package com.capstone.personalmedicalrecord.ui.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.personalmedicalrecord.core.domain.usecase.PatientUseCase
import com.capstone.personalmedicalrecord.core.domain.usecase.StaffUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class LoginViewModel(
    private val patientUseCase: PatientUseCase,
    private val staffUseCase: StaffUseCase,
) : ViewModel() {
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    fun setFirstName(name: String) {
        _email.value = name
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    val isSubmitEnabled: Flow<Boolean> = combine(_email, _password) { email, password ->
        val isEmailCorrect = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordCorrect = password.length >= 5
        return@combine isEmailCorrect and isPasswordCorrect
    }

    fun checkPatient(email: String) = patientUseCase.getPatient(email).asLiveData()

    fun checkStaff(email: String) = staffUseCase.getStaff(email).asLiveData()
}