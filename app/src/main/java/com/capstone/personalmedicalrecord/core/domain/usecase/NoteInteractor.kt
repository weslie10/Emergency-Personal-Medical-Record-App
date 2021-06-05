package com.capstone.personalmedicalrecord.core.domain.usecase

import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.core.domain.repository.IRepository

class NoteInteractor(private val repository: IRepository) : NoteUseCase {
    override fun getNotes(idPatient: String) = repository.getNotes(idPatient)

    override fun getNoteDetail(id: String) = repository.getNoteDetail(id)

    override fun insertNote(note: Note) = repository.insertNote(note)

    override fun updateNote(note: Note) = repository.updateNote(note)

    override fun deleteNote(id: String) = repository.deleteNote(id)
}