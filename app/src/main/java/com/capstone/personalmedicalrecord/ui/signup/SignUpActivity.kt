package com.capstone.personalmedicalrecord.ui.signup

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.personalmedicalrecord.PatientActivity
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.databinding.ActivitySignUpBinding
import com.capstone.personalmedicalrecord.ui.login.LoginActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var preference: MyPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        preference = MyPreference(this)

        binding.loginBtn.setOnClickListener {
//            val email = binding.inputEmail.text.toString()
//            if (email != "") {
//                preference.setz(email)
//
//                val intent = Intent(this@SignUpActivity, PatientActivity::class.java)
//                startActivity(intent)
//            }
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
}