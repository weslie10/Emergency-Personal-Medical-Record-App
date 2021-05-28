package com.capstone.personalmedicalrecord.ui.signup

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
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
import com.capstone.personalmedicalrecord.ui.login.LoginViewModel
import com.capstone.personalmedicalrecord.utils.DataDummy
import com.capstone.personalmedicalrecord.utils.Utility.setColor
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var preference: MyPreference
    private val viewModel: SignUpViewModel by viewModels()
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
            val password = binding.inputPassword.text.toString()
            checkUser(email, password)
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

    private fun checkUser(email: String, password: String) {
        var used = true
        if(role == "Patient") {
            val patient = DataDummy.listPatient.filter { patient ->
                patient.email == email
            }
            if (patient.isEmpty()) {
                setUser(email, password)
                used = false
            }
        }
        else {
            val staff = DataDummy.listStaff.filter { staff ->
                staff.email == email
            }
            if (staff.isEmpty()) {
                setUser(email, password)
                used = false
            }
        }

        if (used){
            MaterialAlertDialogBuilder(this)
                .setMessage(getString(R.string.email_used))
                .setPositiveButton(getString(R.string.ok), null)
                .show()
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

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.isSubmitEnabled.collect { value ->
                binding.signupBtn.isEnabled = value
            }
        }
    }

    private fun setUser(email: String, password: String) {
        preference.setRole(role)
        if (role == "Patient") {
            preference.setId(DataDummy.listPatient.size + 1)
            DataDummy.listPatient.add(
                Patient(
                    preference.getId(),
                    email.split("@")[0],
                    email,
                    password,
                    "",
                    "",
                    "",
                    "",
                    ""
                ),
            )
            startActivity(Intent(this, PatientActivity::class.java))
        } else {
            preference.setId(DataDummy.listStaff.size + 1)
            DataDummy.listStaff.add(
                Staff(
                    preference.getId(),
                    email.split("@")[0],
                    email,
                    password,
                    "",
                    "",
                ),
            )
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