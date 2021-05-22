package com.capstone.personalmedicalrecord.core.data.source.local

import com.capstone.personalmedicalrecord.core.data.source.local.entity.NoteEntity
import com.capstone.personalmedicalrecord.core.data.source.local.room.NoteDao

class LocalDataSource(private val noteDao: NoteDao) {

    fun getNotes() = noteDao.getNotes()

    fun getNote(id: Int) = noteDao.getNote(id)

    fun insertNote(note: NoteEntity) = noteDao.insertNote(note)

    fun updateNote(note: NoteEntity) = noteDao.updateNote(note)

    fun deleteNote(note: NoteEntity) = noteDao.deleteNote(note)
}