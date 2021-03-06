package com.capstone.personalmedicalrecord.ui.patient.data.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.personalmedicalrecord.core.domain.usecase.NoteUseCase

class NotesViewModel(private val noteUseCase: NoteUseCase) : ViewModel() {

    fun getNotes(idPatient: String) = noteUseCase.getNotes(idPatient).asLiveData()

    fun getNoteDetail(id: String) = noteUseCase.getNoteDetail(id).asLiveData()

    fun deleteNote(id: String) = noteUseCase.deleteNote(id)
}