package com.capstone.personalmedicalrecord.core.domain.repository

import com.capstone.personalmedicalrecord.core.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getNotes(): Flow<List<Note>>
    fun getNote(id: Int): Flow<Note>
    fun insertNote(note: Note)
    fun updateNote(note: Note)
    fun deleteNote(note: Note)
}