package com.capstone.personalmedicalrecord.ui.patient.data.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.core.domain.usecase.NoteUseCase

class NoteAddUpdateViewModel(
    private val noteUseCase: NoteUseCase,
) : ViewModel() {

    fun insert(note: Note) = noteUseCase.insertNote(note)

    fun update(note: Note) = noteUseCase.updateNote(note)

    fun getNote(id: String) = noteUseCase.getNoteDetail(id).asLiveData()
}