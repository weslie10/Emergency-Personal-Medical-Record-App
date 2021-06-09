package com.capstone.personalmedicalrecord.ui.login

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.PatientActivity
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.StaffActivity
import com.capstone.personalmedicalrecord.databinding.ActivityLoginBinding
import com.capstone.personalmedicalrecord.ui.signup.SignUpActivity
import com.capstone.personalmedicalrecord.utils.Utility.setColor
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var preference: MyPreference
    private val viewModel: LoginViewModel by viewModel()
    private var check = false
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
            check = false
            val email = binding.inputEmail.text.toString()
            viewModel.checkPatient(email).observe(this, { it1 ->
                if (it1.data != null) {
                    val patient = it1.data
                    if (patient.id != "") {
                        if (patient.password == binding.inputPassword.text.toString()) {
                            check = true
                            preference.setId(patient.id)
                            preference.setRole("Patient")
                            startActivity(Intent(this, PatientActivity::class.java))
                            finish()
                        } else {
                            check = true
                            binding.inputPassword.error = "Wrong Password"
                        }
                    } else {
                        viewModel.checkStaff(email).observe(this, { it2 ->
                            if (it2.data != null) {
                                check = true
                                val staff = it2.data
                                if (staff.id != "") {
                                    if (staff.password == binding.inputPassword.text.toString()) {
                                        check = true
                                        preference.setId(staff.id)
                                        preference.setRole("Staff")
                                        startActivity(Intent(this, StaffActivity::class.java))
                                        finish()
                                    } else {
                                        check = true
                                        binding.inputPassword.error = "Wrong Password"
                                    }
                                } else if (!check) {
                                    MaterialAlertDialogBuilder(this)
                                        .setMessage(getString(R.string.email_not_found))
                                        .setPositiveButton(getString(R.string.ok), null)
                                        .show()
                                }
                            }
                        })
                    }
                }
            })
        }

        binding.signupTxt.apply {
            paintFlags =
                paintFlags or Paint.UNDERLINE_TEXT_FLAG

            setOnClickListener {
                val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(intent)
                this@LoginActivity.finish()
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

    override fun onBackPressed() {
        MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.exit_text))
            .setNegativeButton(getString(R.string.no), null)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                super.onBackPressed()
            }
            .show()
    }
}