package com.capstone.personalmedicalrecord.utils

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.capstone.personalmedicalrecord.R
import com.google.android.material.textfield.TextInputLayout
import java.time.LocalDate
import java.time.LocalDateTime
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
        this.text = getDate()
    }

    fun getDate(): String {
        val dateTime = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM y")
        return dateTime.format(formatter)
    }

    fun getDatetime(): String {
        val dateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM y HH:mm:ss")
        return dateTime.format(formatter)
    }

    fun String.convertEmpty(): String {
        return if (this != "") this else "-"
    }

    fun View.hideKeyboard() {
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun ImageView.setImage(picture: String) {
        if (picture.length > 2) {
            Glide.with(context)
                .load(picture)
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .centerCrop()
                .into(this)
        } else {
            Glide.with(context)
                .load(R.drawable.user)
                .centerCrop()
                .into(this)
        }
    }
}