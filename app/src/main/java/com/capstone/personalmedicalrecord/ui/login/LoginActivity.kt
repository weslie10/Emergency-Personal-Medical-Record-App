package com.capstone.personalmedicalrecord.ui.login

import android.R
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.capstone.personalmedicalrecord.PatientActivity
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.StaffActivity
import com.capstone.personalmedicalrecord.databinding.ActivityLoginBinding
import com.capstone.personalmedicalrecord.ui.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var preference: MyPreference
    private var role: String = "Patient"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        preference = MyPreference(this)
        setSpinner()
        binding.loginBtn.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            if (email != "") {
                preference.setEmail(email)
                preference.setRole(role)
                when (role) {
                    "Patient" -> startActivity(Intent(this, PatientActivity::class.java))
                    "Staff" -> startActivity(Intent(this, StaffActivity::class.java))
                }
                finish()
            }
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

    private fun setSpinner() {
        val list = arrayOf("Patient", "Staff")
        val arrayAdapter =
            ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, list)
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

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }
    }
}