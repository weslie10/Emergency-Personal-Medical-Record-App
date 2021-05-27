package com.capstone.personalmedicalrecord.ui.login

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.PatientActivity
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.StaffActivity
import com.capstone.personalmedicalrecord.databinding.ActivityLoginBinding
import com.capstone.personalmedicalrecord.ui.signup.SignUpActivity
import com.capstone.personalmedicalrecord.utils.DataDummy
import com.capstone.personalmedicalrecord.utils.Utility.setColor
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var preference: MyPreference
    private val viewModel: LoginViewModel by viewModels()
    private var emailError = false
    private var passwordError = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        preference = MyPreference(this)

        initListeners()
        initObserver()

        binding.loginBtn.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            checkUser(email, password)
        }

        binding.signupTxt.apply {
            paintFlags =
                paintFlags or Paint.UNDERLINE_TEXT_FLAG

            setOnClickListener {
                val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun initListeners() {
        binding.inputEmail.addTextChangedListener {
            viewModel.setFirstName(it.toString())
            if (it.toString() != "") {
                emailError = false
                if (!Patterns.EMAIL_ADDRESS.matcher(it.toString()).matches()) {
                    emailError = true
                    binding.inputEmail.error = "Incorrect format for Email"
                }
            } else {
                emailError = true
                binding.inputEmail.error = "Email Can't Be Blank"
            }

            if (emailError) {
                binding.emailTxt.setColor(R.color.red)
            } else {
                binding.emailTxt.setColor(R.color.blue)
            }
        }

        binding.inputPassword.addTextChangedListener {
            viewModel.setPassword(it.toString())
            if (it.toString() != "") {
                passwordError = false
                if (it.toString().length < 5) {
                    passwordError = true
                    binding.inputPassword.error =
                        "Password Length should be greater than 5 characters"
                }
            } else {
                passwordError = true
                binding.inputPassword.error = "Password Can't Be Blank"
            }

            if (passwordError) {
                binding.passwordTxt.setColor(R.color.red)
            } else {
                binding.passwordTxt.setColor(R.color.blue)
            }
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.isSubmitEnabled.collect { value ->
                binding.loginBtn.isEnabled = value
            }
        }
    }

    private fun checkUser(email: String, password: String) {
        val patient = DataDummy.listPatient.filter { patient ->
            patient.email == email
        }
        if (patient.isNotEmpty()) {
            if (patient[0].password == password) {
                preference.setId(patient[0].id)
                preference.setRole("Patient")
                startActivity(Intent(this, PatientActivity::class.java))
                finish()
            } else {
                binding.inputPassword.error = "Wrong Password"
            }
        } else {
            val staff = DataDummy.listStaff.filter { staff ->
                staff.email == email
            }
            if (staff.isNotEmpty()) {
                if (staff[0].password == password) {
                    preference.setId(staff[0].id)
                    preference.setRole("Staff")
                    startActivity(Intent(this, StaffActivity::class.java))
                    finish()
                } else {
                    binding.inputPassword.error = "Wrong Password"
                }
            } else {
                MaterialAlertDialogBuilder(this)
                    .setMessage(getString(R.string.email_not_found))
                    .setPositiveButton(getString(R.string.ok), null)
                    .show()
            }
        }
    }
}