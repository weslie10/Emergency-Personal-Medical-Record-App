package com.capstone.personalmedicalrecord.utils

import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.capstone.personalmedicalrecord.R

object Utility {
    fun FragmentActivity.navigateTo(fragment: Fragment, id: Int) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.nav_default_enter_anim,
                R.anim.nav_default_exit_anim,
                R.anim.nav_default_pop_enter_anim,
                R.anim.nav_default_pop_exit_anim
            )
            .replace(id, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun FragmentActivity.clickBack(btn: ImageButton) {
        btn.setOnClickListener {
            supportFragmentManager.popBackStack()
        }
    }
}