package com.capstone.personalmedicalrecord

import android.content.Context
import androidx.core.content.edit

internal class MyPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val ID = "id"
        private const val ROLE = "role"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setId(id: Int) {
        preferences.edit {
            putInt(ID, id)
        }
    }

    fun getId(): Int = preferences.getInt(ID, 0)

    fun setRole(role: String) {
        preferences.edit {
            putString(ROLE, role)
        }
    }

    fun getRole(): String? = preferences.getString(ROLE, "")
}