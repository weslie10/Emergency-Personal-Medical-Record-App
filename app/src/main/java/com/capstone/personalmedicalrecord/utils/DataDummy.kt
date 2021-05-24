package com.capstone.personalmedicalrecord.utils

import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.core.domain.model.Patient
import com.capstone.personalmedicalrecord.core.domain.model.Record
import com.capstone.personalmedicalrecord.core.domain.model.Staff

object DataDummy {

    fun generateDummyRecords(): List<Record> {
        return arrayListOf(
            Record(
                "Saturday, 15 May 2021",
                generateDummyText(),
                "Rumah Sakit Santa Maria"
            ),
            Record(
                "Sunday, 16 May 2021",
                generateDummyText(),
                "Rumah Sakit Awal Bros"
            ),
            Record(
                "Monday, 17 May 2021",
                generateDummyText(),
                "Rumah Sakit Prima"
            )
        )
    }

    fun generateDummyNotes(): List<Note> {
        return arrayListOf(
            Note(
                1,
                "Saturday, 15 May 2021",
                "Aku sehat"
            ),
            Note(
                2,
                "Sunday, 16 May 2021",
                "Aku sakit"
            ),
            Note(
                3,
                "Monday, 17 May 2021",
                "Aku meninggal"
            )
        )
    }

    fun getDataPatient(): List<Patient> {
        return arrayListOf(
            Patient(
                1,
                "Weslie Leonardo",
                "weslie@gmail.com",
                "12345",
                "081234567890",
                "10 September 2000",
                "Jl. Nurul Ikhlas",
                "Male",
                "A"
            ),
            Patient(
                2,
                "Winli",
                "winli@gmail.com",
                "12345",
                "081234567890",
                "25 April 2000",
                "Jl. Jendral",
                "Male",
                "A"
            )
        )
    }

    fun getDataStaff(): List<Staff> {
        return arrayListOf(
            Staff(
                1,
                "Joni Dunita",
                "joni@gmail.com",
                "12345",
                "081234567890",
                "RS Suka Maju Gak Suka Mundur",
            ),
            Staff(
                2,
                "Amin yang paling berkah",
                "amin@gmail.com",
                "12345",
                "081234567890",
                "RS Itu Itu Mulu",
            )
        )
    }

    private fun generateDummyText(): String {
        return "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus imperdiet pharetra tortor, a iaculis purus dignissim ut. Suspendisse bibendum aliquam nulla, eu tempus dui fringilla eu."
    }
}