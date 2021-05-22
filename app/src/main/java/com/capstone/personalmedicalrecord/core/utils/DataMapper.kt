package com.capstone.personalmedicalrecord.core.utils

import com.capstone.personalmedicalrecord.core.data.source.local.entity.NoteEntity
import com.capstone.personalmedicalrecord.core.domain.model.Note

object DataMapper {

    fun mapNoteToEntity(input: Note) = NoteEntity(
        id = input.id,
        date = input.date,
        description = input.description
    )

    fun mapNoteEntitiesToDomain(input: List<NoteEntity>): List<Note> =
        input.map {
            mapNoteEntityToDomain(it)
        }

    fun mapNoteEntityToDomain(input: NoteEntity): Note = Note(
        id = input.id,
        date = input.date,
        description = input.description
    )
}