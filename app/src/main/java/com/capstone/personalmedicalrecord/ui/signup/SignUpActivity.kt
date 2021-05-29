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

        binding.inputRepeat.addTextChangedListener {
            viewModel.setRepeat(it.toString())
            if (it.toString() != "") {
                repeatError = false
                if (it.toString() != binding.inputPassword.text.toString()) {
                    repeatError = true
                    binding.inputRepeat.error =
                        "Password is not same as repeat"
                }
            } else {
                repeatError = true
                binding.inputRepeat.error = "Password Can't Be Blank"
            }

            if (repeatError) {
                binding.repeatTxt.setColor(R.color.red)
            } else {
                binding.repeatTxt.setColor(R.color.blue)
            }
        }
    }

    private fun checkUser(email: String) {
        if (role == "Patient") {
            viewModel.setEmailPatient(email)
        } else {
            viewModel.setEmailStaff(email)
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.isSubmitEnabled.collect { value ->
                binding.signupBtn.isEnabled = value
            }
        }

        viewModel.existingPatient.observe(this, { patient ->
            if (patient.id != 0) {
                viewModel.existingStaff.observe(this, { staff ->
                    if (staff.id != 0) {
                        MaterialAlertDialogBuilder(this)
                            .setMessage(getString(R.string.email_used))
                            .setPositiveButton(getString(R.string.ok), null)
                            .show()
                    } else if (binding.signupBtn.isEnabled) {
                        setUser(binding.inputEmail.text.toString(), binding.inputPassword.text.toString())
                    }
                })
            } else if (binding.signupBtn.isEnabled) {
                setUser(binding.inputEmail.text.toString(), binding.inputPassword.text.toString())
            }
        })
    }

    private fun setUser(email: String, password: String) {
        preference.setRole(role)
        if (role == "Patient") {
            val patient = Patient(name = email.split("@")[0], email = email, password = password)
            lifecycleScope.launch(Dispatchers.IO) {
                val id = viewModel.insertPatient(patient)
                preference.setId(id)
                Log.d("patient",id.toString())
            }
            startActivity(Intent(this, PatientActivity::class.java))
        } else {
            val staff = Staff(name = email.split("@")[0], email = email, password = password)
            lifecycleScope.launch(Dispatchers.IO) {
                val id = viewModel.insertStaff(staff)
                preference.setId(id)
                Log.d("staff",id.toString())
            }
            startActivity(Intent(this, StaffActivity::class.java))
        }
        finish()
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
                    id: Long
                ) {
                    role = list[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }
}