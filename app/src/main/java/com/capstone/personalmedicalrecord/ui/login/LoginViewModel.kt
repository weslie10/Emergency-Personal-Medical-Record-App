package com.capstone.personalmedicalrecord.ui.login

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.personalmedicalrecord.core.domain.usecase.PatientUseCase
import com.capstone.personalmedicalrecord.core.domain.usecase.StaffUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class LoginViewModel(
    private val patientUseCase: PatientUseCase,
    private val staffUseCase: StaffUseCase
) : ViewModel() {
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _emailPatient = MutableLiveData<String>()
    private val _emailStaff = MutableLiveData<String>()

    fun setFirstName(name: String) {
        _email.value = name
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setEmailPatient(email: String) {
        _emailPatient.value = email
    }

    fun setEmailStaff(email: String) {
        _emailStaff.value = email
    }

    val isSubmitEnabled: Flow<Boolean> = combine(_email, _password) { email, password ->
        val isEmailCorrect = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordCorrect = password.length >= 5
        return@combine isEmailCorrect and isPasswordCorrect
    }

    val existingPatient = Transformations.switchMap(_emailPatient) {
        patientUseCase.getPatient(it).asLiveData()
    }

    val existingStaff = Transformations.switchMap(_emailStaff) {
        staffUseCase.getStaff(it).asLiveData()
    }
}