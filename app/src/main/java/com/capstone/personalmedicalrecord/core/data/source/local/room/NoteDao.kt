package com.capstone.personalmedicalrecord.core.data.source.local.room

import androidx.room.*
import com.capstone.personalmedicalrecord.core.data.source.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note WHERE idPatient=:idPatient ORDER BY datetime DESC")
    fun getNotes(idPatient: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note WHERE id=:id")
    fun getNote(id: String): Flow<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNote(note: NoteEntity)

    @Update
    fun updateNote(note: NoteEntity)

    @Query("DELETE FROM note WHERE id=:id")
    fun deleteNote(id: String)
}