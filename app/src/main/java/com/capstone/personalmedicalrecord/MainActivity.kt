package com.capstone.personalmedicalrecord

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.personalmedicalrecord.databinding.ActivityMainBinding
import com.capstone.personalmedicalrecord.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var preference: MyPreference
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = MyPreference(this)

        if (preference.getEmail() == "") {
            moveToLogin()
        }

        binding.logoutBtn.setOnClickListener {
            preference.setEmail("")
            moveToLogin()
        }
    }

    private fun moveToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}