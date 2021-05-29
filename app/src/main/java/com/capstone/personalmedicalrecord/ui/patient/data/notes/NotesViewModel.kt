package com.capstone.personalmedicalrecord.ui.patient.data.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.personalmedicalrecord.core.domain.usecase.NoteUseCase

class NotesViewModel(noteUseCase: NoteUseCase) : ViewModel() {

    val notes = noteUseCase.getNotes().asLiveData()
}