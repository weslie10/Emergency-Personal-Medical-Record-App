package com.capstone.personalmedicalrecord.ui.patient.data

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.personalmedicalrecord.ui.patient.data.notes.NotesFragment
import com.capstone.personalmedicalrecord.ui.patient.data.records.RecordsFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = RecordsFragment()
            1 -> fragment = NotesFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}