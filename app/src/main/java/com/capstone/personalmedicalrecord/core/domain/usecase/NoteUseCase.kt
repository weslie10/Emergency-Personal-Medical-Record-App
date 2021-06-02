package com.capstone.personalmedicalrecord.core.domain.usecase

import com.capstone.personalmedicalrecord.core.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteUseCase {
    fun getNotes(idPatient: String): Flow<List<Note>>
    fun getNote(id: String): Flow<Note>
    fun insertNote(note: Note)
    fun updateNote(note: Note)
    fun deleteNote(id: String)
}