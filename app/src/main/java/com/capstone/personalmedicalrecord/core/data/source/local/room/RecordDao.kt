package com.capstone.personalmedicalrecord.core.data.source.local.room

import androidx.room.*
import com.capstone.personalmedicalrecord.core.data.source.local.entity.RecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {

    @Query("SELECT * FROM record WHERE idPatient=:idPatient ORDER BY date DESC")
    fun getRecords(idPatient: String): Flow<List<RecordEntity>>

    @Query("SELECT * FROM record WHERE id=:id")
    fun getRecord(id: String): Flow<RecordEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecord(record: RecordEntity)

    @Update
    fun updateRecord(record: RecordEntity)

    @Query("DELETE FROM record WHERE id=:id")
    fun deleteRecord(id: String)
}