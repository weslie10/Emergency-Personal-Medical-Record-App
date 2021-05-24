package com.capstone.personalmedicalrecord.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.capstone.personalmedicalrecord.PatientActivity
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.StaffActivity
import com.capstone.personalmedicalrecord.ui.login.LoginActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var preference: MyPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        preference = MyPreference(this)

        window.setFlags(
            FULL_SCREEN_FLAG,
            FULL_SCREEN_FLAG
        )
        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            if (preference.getId() == 0) {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                when (preference.getRole()) {
                    "Patient" -> startActivity(Intent(this, PatientActivity::class.java))
                    "Staff" -> startActivity(Intent(this, StaffActivity::class.java))
                }
            }
            finish()
        }, TIME)
    }

    companion object {
        private const val TIME = 3000L
        private const val FULL_SCREEN_FLAG = 1024
    }
}