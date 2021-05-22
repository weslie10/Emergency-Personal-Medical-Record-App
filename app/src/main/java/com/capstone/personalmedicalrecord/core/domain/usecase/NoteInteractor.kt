package com.capstone.personalmedicalrecord.core.domain.usecase

import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.core.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow

class NoteInteractor(private val repository: IRepository) : NoteUseCase {
    override fun getNotes() = repository.getNotes()

    override fun getNote(id: Int): Flow<Note> = repository.getNote(id)

    override fun insertNote(note: Note) = repository.insertNote(note)

    override fun updateNote(note: Note) = repository.updateNote(note)

    override fun deleteNote(note: Note) = repository.deleteNote(note)
}