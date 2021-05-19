package com.capstone.personalmedicalrecord

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.capstone.personalmedicalrecord.databinding.ActivityMainBinding
import com.capstone.personalmedicalrecord.ui.data.DataFragment
import com.capstone.personalmedicalrecord.ui.home.HomeFragment
import com.capstone.personalmedicalrecord.ui.login.LoginActivity
import com.capstone.personalmedicalrecord.ui.other.OtherFragment
import com.capstone.personalmedicalrecord.ui.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
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

        loadFragment(HomeFragment())
        binding.bottomNav.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> loadFragment(HomeFragment())
            R.id.navigation_data -> loadFragment(DataFragment())
            R.id.navigation_profile -> loadFragment(ProfileFragment())
            R.id.navigation_other -> loadFragment(OtherFragment())
        }
        return true
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.apply {
                beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
            }

            return true
        }
        return false
    }

    private fun moveToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0){
            MaterialAlertDialogBuilder(this)
                .setMessage(getString(R.string.exit_text))
                .setNegativeButton(getString(R.string.no), null)
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    super.onBackPressed()
                }
                .show()
        }
        else {
            supportFragmentManager.popBackStack()
        }
    }
}