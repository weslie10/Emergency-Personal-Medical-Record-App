package com.capstone.personalmedicalrecord.ui.login

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.PatientActivity
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.StaffActivity
import com.capstone.personalmedicalrecord.databinding.ActivityLoginBinding
import com.capstone.personalmedicalrecord.ui.signup.SignUpActivity
import com.capstone.personalmedicalrecord.utils.DataDummy
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var preference: MyPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        preference = MyPreference(this)
//        setSpinner()
        binding.loginBtn.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            checkAvailable(email, password)
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

    private fun checkAvailable(email: String, password: String) {
        if (email != "") {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (password != "") {
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
                } else {
                    binding.inputPassword.error = "Password Can't Be Blank"
                }
            } else {
                binding.inputEmail.error = "Email Must Have At Least @"
            }
        } else {
            binding.inputEmail.error = "Email Can't Be Blank"
        }
    }
}