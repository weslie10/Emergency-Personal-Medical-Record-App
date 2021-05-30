package com.capstone.personalmedicalrecord.ui.signup

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.personalmedicalrecord.core.domain.model.Patient
import com.capstone.personalmedicalrecord.core.domain.model.Staff
import com.capstone.personalmedicalrecord.core.domain.usecase.PatientUseCase
import com.capstone.personalmedicalrecord.core.domain.usecase.StaffUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class SignUpViewModel(
    private val patientUseCase: PatientUseCase,
    private val staffUseCase: StaffUseCase
) : ViewModel() {
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _repeat = MutableStateFlow("")

    private val _emailPatient = MutableLiveData<String>()
    private val _emailStaff = MutableLiveData<String>()

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setRepeat(repeat: String) {
        _repeat.value = repeat
    }

    fun setEmailPatient(email: String) {
        _emailPatient.value = email
    }

    fun setEmailStaff(email: String) {
        _emailStaff.value = email
    }

    val isSubmitEnabled: Flow<Boolean> =
        combine(_email, _password, _repeat) { email, password, repeat ->
            val isEmailCorrect = Patterns.EMAIL_ADDRESS.matcher(email).matches()
            val isPasswordCorrect = password.length >= 5
            val isPasswordSame = password == repeat
            return@combine isEmailCorrect and isPasswordCorrect and isPasswordSame
        }

    fun insertPatient(patient: Patient) = patientUseCase.insertPatient(patient)

    fun insertStaff(staff: Staff) = staffUseCase.insertStaff(staff)

    fun checkPatient(email: String) = patientUseCase.getPatient(email).asLiveData()
    fun checkStaff(email: String) = staffUseCase.getStaff(email).asLiveData()

    val existingPatient = Transformations.switchMap(_emailPatient) {
        patientUseCase.getPatient(it).asLiveData()
    }

    val existingStaff = Transformations.switchMap(_emailStaff) {
        staffUseCase.getStaff(it).asLiveData()
    }
}