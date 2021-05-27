package com.capstone.personalmedicalrecord.core.domain.usecase

import com.capstone.personalmedicalrecord.core.domain.model.Record
import kotlinx.coroutines.flow.Flow

interface RecordUseCase {
    fun getRecords(): Flow<List<Record>>
    fun getRecord(id: Int): Flow<Record>
    fun insertRecord(record: Record)
    fun updateRecord(record: Record)
    fun deleteRecord(record: Record)
}