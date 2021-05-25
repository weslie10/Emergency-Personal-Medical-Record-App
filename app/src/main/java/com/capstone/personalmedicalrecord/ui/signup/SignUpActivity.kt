package com.capstone.personalmedicalrecord.ui.signup

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.PatientActivity
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.StaffActivity
import com.capstone.personalmedicalrecord.core.domain.model.Patient
import com.capstone.personalmedicalrecord.core.domain.model.Staff
import com.capstone.personalmedicalrecord.databinding.ActivitySignUpBinding
import com.capstone.personalmedicalrecord.ui.login.LoginActivity
import com.capstone.personalmedicalrecord.utils.DataDummy
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var preference: MyPreference
    private var role = "Patient"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        preference = MyPreference(this)

        setSpinner()

        binding.loginBtn.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            if (email != "") {
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (password != "") {
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

        binding.loginTxt.apply {
            paintFlags =
                paintFlags or Paint.UNDERLINE_TEXT_FLAG

            setOnClickListener {
                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                startActivity(intent)
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