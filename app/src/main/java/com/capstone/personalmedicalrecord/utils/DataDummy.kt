package com.capstone.personalmedicalrecord.utils

import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.core.domain.model.Record

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
                "Saturday, 15 May 2021",
                "Aku sehat"
            ),
            Note(
                "Sunday, 16 May 2021",
                "Aku sakit"
            ),
            Note(
                "Monday, 17 May 2021",
                "Aku meninggal"
            )
        )
    }

    fun generateDummyText(): String {
        return "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus imperdiet pharetra tortor, a iaculis purus dignissim ut. Suspendisse bibendum aliquam nulla, eu tempus dui fringilla eu."
    }
}