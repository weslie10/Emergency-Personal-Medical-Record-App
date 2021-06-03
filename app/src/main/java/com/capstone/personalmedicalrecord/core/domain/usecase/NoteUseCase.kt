package com.capstone.personalmedicalrecord.core.domain.usecase

import com.capstone.personalmedicalrecord.core.data.Resource
import com.capstone.personalmedicalrecord.core.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteUseCase {
    fun getNotes(idPatient: String): Flow<Resource<List<Note>>>
    fun getNoteDetail(id: String): Flow<Resource<Note>>
    fun insertNote(note: Note)
    fun updateNote(note: Note)
    fun deleteNote(id: String)
}