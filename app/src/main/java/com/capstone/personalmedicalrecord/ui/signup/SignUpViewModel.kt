package com.capstone.personalmedicalrecord.ui.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class SignUpViewModel: ViewModel() {
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _repeat = MutableStateFlow("")

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setRepeat(repeat: String) {
        _repeat.value = repeat
    }

    val isSubmitEnabled: Flow<Boolean> = combine(_email, _password, _repeat) { email, password, repeat ->
        val isEmailCorrect = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordCorrect = password.length >= 5
        val isPasswordSame = password == repeat
        return@combine isEmailCorrect and isPasswordCorrect and isPasswordSame
    }
}