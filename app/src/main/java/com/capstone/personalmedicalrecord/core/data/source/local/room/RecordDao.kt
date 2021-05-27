package com.capstone.personalmedicalrecord.core.data.source.local.room

import androidx.room.*
import com.capstone.personalmedicalrecord.core.data.source.local.entity.RecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {

    @Query("SELECT * FROM record ORDER BY datetime DESC")
    fun getRecords(): Flow<List<RecordEntity>>

    @Query("SELECT * FROM record WHERE id=:id")
    fun getRecord(id: Int): Flow<RecordEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecord(record: RecordEntity)

    @Update
    fun updateRecord(record: RecordEntity)

    @Delete
    fun deleteRecord(record: RecordEntity)
}