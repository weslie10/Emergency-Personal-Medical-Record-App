package com.capstone.personalmedicalrecord.core.domain.usecase

import com.capstone.personalmedicalrecord.core.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteUseCase {
    fun getNotes(): Flow<List<Note>>
    fun getNote(id: Int): Flow<Note>
    fun insertNote(note: Note)
    fun updateNote(note: Note)
    fun deleteNote(note: Note)
}