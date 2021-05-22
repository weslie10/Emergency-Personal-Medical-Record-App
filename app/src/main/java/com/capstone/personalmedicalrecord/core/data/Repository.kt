package com.capstone.personalmedicalrecord.core.data

import com.capstone.personalmedicalrecord.core.data.source.local.LocalDataSource
import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.core.domain.repository.IRepository
import com.capstone.personalmedicalrecord.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repository(
    private val localDataSource: LocalDataSource
) : IRepository {
    override fun getNotes(): Flow<List<Note>> =
        localDataSource.getNotes().map {
            DataMapper.mapNoteEntitiesToDomain(it)
        }

    override fun getNote(id: Int): Flow<Note> =
        localDataSource.getNote(id).map {
            DataMapper.mapNoteEntityToDomain(it)
        }

    override fun insertNote(note: Note) {
        val noteEntity = DataMapper.mapNoteToEntity(note)
        localDataSource.insertNote(noteEntity)
    }

    override fun updateNote(note: Note) {
        val noteEntity = DataMapper.mapNoteToEntity(note)
        localDataSource.updateNote(noteEntity)
    }

    override fun deleteNote(note: Note) {
        val noteEntity = DataMapper.mapNoteToEntity(note)
        localDataSource.deleteNote(noteEntity)
    }
}