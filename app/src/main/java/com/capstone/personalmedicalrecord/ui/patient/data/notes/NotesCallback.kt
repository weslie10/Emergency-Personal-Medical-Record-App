package com.capstone.personalmedicalrecord.ui.patient.data.notes

import com.capstone.personalmedicalrecord.core.domain.model.Note

interface NotesCallback {
    fun onItemClick(note: Note)
}