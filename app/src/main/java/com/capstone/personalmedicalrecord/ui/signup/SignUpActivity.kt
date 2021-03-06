package com.capstone.personalmedicalrecord.ui.signup

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.PatientActivity
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.StaffActivity
import com.capstone.personalmedicalrecord.core.domain.model.Patient
import com.capstone.personalmedicalrecord.core.domain.model.Staff
import com.capstone.personalmedicalrecord.databinding.ActivitySignUpBinding
import com.capstone.personalmedicalrecord.ui.login.LoginActivity
import com.capstone.personalmedicalrecord.utils.Utility.setColor
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var preference: MyPreference
    private val viewModel: SignUpViewModel by viewModel()
    private var emailError = false
    private var passwordError = false
    private var repeatError = false
    private var check = false
    private var role = "Patient"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        preference = MyPreference(this)

        initListeners()
        initObserver()
        setSpinner()

        binding.signupBtn.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            checkUser(email)
        }

        binding.loginTxt.apply {
            paintFlags =
                paintFlags or Paint.UNDERLINE_TEXT_FLAG

            setOnClickListener {
                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                startActivity(intent)
                this@SignUpActivity.finish()
            }
        }
    }

    private fun initListeners() {
        binding.inputEmail.addTextChangedListener {
            viewModel.setEmail(it.toString())
            if (it.toString() != "") {
                emailError = false
                if (!Patterns.EMAIL_ADDRESS.matcher(it.toString()).matches()) {
                    emailError = true
                    binding.inputEmail.error = "Incorrect format for email"
                }
            } else {
                emailError = true
                binding.inputEmail.error = "Email can't be blank"
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
                        "Password length should be greater than 5 characters"
                }
            } else {
                passwordError = true
                binding.inputPassword.error = "Password can't be blank"
            }

            if (passwordError) {
                binding.passwordTxt.setColor(R.color.red)
            } else {
                binding.passwordTxt.setColor(R.color.blue)
            }
        }

        binding.inputRepeat.addTextChangedListener {
            viewModel.setRepeat(it.toString())
            if (it.toString() != "") {
                repeatError = false
                if (it.toString() != binding.inputPassword.text.toString()) {
                    repeatError = true
                    binding.inputRepeat.error =
                        "Password and confirmation do not match"
                }
            } else {
                repeatError = true
                binding.inputRepeat.error = "Password can't be blank"
            }

            if (repeatError) {
                binding.repeatTxt.setColor(R.color.red)
            } else {
                binding.repeatTxt.setColor(R.color.blue)
            }
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.isSubmitEnabled.collect { value ->
                binding.signupBtn.isEnabled = value
            }
        }
    }

    private fun setSpinner() {
        val list = arrayOf("Patient", "Staff")
        val arrayAdapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list)
        binding.spRole.apply {
            adapter = arrayAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    role = list[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    private fun checkUser(email: String) {
        var p: Boolean? = null
        var s: Boolean? = null
        check = false

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.checkPatient(email).observe(this@SignUpActivity, { patient ->
                if (patient.data != null) {
                    if (!check) {
                        p = patient.data.id == ""
                        Log.d("check1", "p: ${p.toString()}, s: ${s.toString()}, check: $check")
                        check(p, s)
                    }
                }
            })
            viewModel.checkStaff(email).observe(this@SignUpActivity, { staff ->
                if (staff.data != null) {
                    if (!check) {
                        s = staff.data.id == ""
                        Log.d("check2", "p: ${p.toString()}, s: ${s.toString()}, check: $check")
                        check(p, s)
                    }
                }
            })
        }
    }

    private fun check(p: Boolean?, s: Boolean?) {
        Log.d("check3", "p: ${p.toString()}, s: ${s.toString()}, check: $check")
        if (p != null && s != null && !check) {
            if (p == true && s == true) {
                check = true
                setUser(
                    binding.inputEmail.text.toString(),
                    binding.inputPassword.text.toString()
                )
            } else {
                check = true
                MaterialAlertDialogBuilder(this@SignUpActivity)
                    .setMessage(getString(R.string.email_used))
                    .setPositiveButton(getString(R.string.ok), null)
                    .show()
            }
        }
    }

    private fun setUser(email: String, password: String) {
        if (role == "Patient") {
            MaterialAlertDialogBuilder(this@SignUpActivity)
                .setTitle("Informed Consent")
                .setMessage("""
                    This application will collect and use your data to perform our services. 

                    Some example of data this application collects and uses are:

                    1. Personal data
                    This may include your name, phone number, birth date, address, gender, and blood type.

                    2. Personal medical record data
                    This may include your medical laboratory results.
                """.trimIndent())
                .setPositiveButton("I agree") { _, _ ->
                    preference.setRole(role)
                    val patient =
                        Patient(name = email.split("@")[0], email = email, password = password)
                    lifecycleScope.launch(Dispatchers.IO) {
                        val id = viewModel.insertPatient(patient)
                        preference.setId(id)
                        startActivity(Intent(this@SignUpActivity, PatientActivity::class.java))
                        this@SignUpActivity.finish()
                    }
                }
                .setNegativeButton("I refuse", null)
                .show()
        } else {
            preference.setRole(role)
            val staff = Staff(name = email.split("@")[0], email = email, password = password)
            lifecycleScope.launch(Dispatchers.IO) {
                val id = viewModel.insertStaff(staff)
                preference.setId(id)
            }
            startActivity(Intent(this, StaffActivity::class.java))
            this.finish()
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