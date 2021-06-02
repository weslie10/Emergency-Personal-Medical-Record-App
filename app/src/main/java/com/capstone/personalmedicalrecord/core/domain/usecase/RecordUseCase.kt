package com.capstone.personalmedicalrecord.core.domain.usecase

import com.capstone.personalmedicalrecord.core.domain.model.Record
import kotlinx.coroutines.flow.Flow

interface RecordUseCase {
    fun getRecords(idPatient: String): Flow<List<Record>>
    fun getRecord(id: String): Flow<Record>
    fun insertRecord(record: Record)
    fun updateRecord(record: Record)
    fun deleteRecord(id: String)
}