package com.capstone.personalmedicalrecord.utils

import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.core.domain.model.Patient
import com.capstone.personalmedicalrecord.core.domain.model.Staff
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object Utility {
    fun FragmentActivity.navigateTo(fragment: Fragment, id: Int) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
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

    fun Int.searchPatient(): Patient {
        return DataDummy.getDataPatient().filter { patient ->
            patient.id == this
        }[0]
    }

    fun Int.searchStaff(): Staff {
        return DataDummy.getDataStaff().filter { staff ->
            staff.id == this
        }[0]
    }

    fun MutableList<String>.simpleText(): String {
        var count = 0
        for (i in 0 until this.size) {
            if (this[i].length + count <= 10) {
                count += this[i].length
                this[i] = this[i].capitalize(Locale.ROOT)
            } else {
                if (count > 10) break
                count += 2
                this[i] = "${this[i][0].toUpperCase()}."
            }
            count += 1
        }
        return this.joinToString(" ")
    }

    fun TextView.dateNow() {
        val dateTime = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM y")
        this.text = dateTime.format(formatter)
    }
}