package com.capstone.personalmedicalrecord

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.capstone.personalmedicalrecord.databinding.ActivityMainBinding
import com.capstone.personalmedicalrecord.ui.home.HomeFragment
import com.capstone.personalmedicalrecord.ui.login.LoginActivity
import com.capstone.personalmedicalrecord.ui.other.OtherFragment
import com.capstone.personalmedicalrecord.ui.profile.ProfileFragment
import com.capstone.personalmedicalrecord.ui.records.RecordsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

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
//        val navView: BottomNavigationView = binding.navView
//
//        val navController = findNavController(R.id.nav_host_fragment_activity_bottom)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home,
//                R.id.navigation_records,
//                R.id.navigation_profile,
//                R.id.navigation_other
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> loadFragment(HomeFragment())
            R.id.navigation_records -> loadFragment(RecordsFragment())
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
}