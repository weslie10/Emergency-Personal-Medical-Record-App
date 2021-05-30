package com.capstone.personalmedicalrecord.utils

import android.content.res.ColorStateList
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.core.domain.model.Patient
import com.capstone.personalmedicalrecord.core.domain.model.Staff
import com.google.android.material.textfield.TextInputLayout
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

    fun TextInputLayout.setColor(color: Int) {
        boxStrokeColor = context.getColor(color)
        hintTextColor = ColorStateList.valueOf(context.getColor(color))
    }

    fun FragmentActivity.clickBack(btn: ImageButton) {
        btn.setOnClickListener {
            supportFragmentManager.popBackStack()
        }
    }

    fun Int.searchPatient(): Patient {
        return DataDummy.listPatient.filter { patient ->
            patient.id == this
        }[0]
    }

    fun Int.searchStaff(): Staff {
        return DataDummy.listStaff.filter { staff ->
            staff.id == this
        }[0]
    }

    fun Int.searchNote(): Note {
        return DataDummy.listNotes.filter { note ->
            note.id == this
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
        this.text = getDatetime()
    }

    fun getDatetime(): String {
        val dateTime = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM y")
        return dateTime.format(formatter)
    }

    fun String.convertEmpty(): String {
        return if (this != "") this else "-"
    }
}