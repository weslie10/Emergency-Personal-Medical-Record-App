package com.capstone.personalmedicalrecord.core.domain.usecase

import com.capstone.personalmedicalrecord.core.data.Resource
import com.capstone.personalmedicalrecord.core.domain.model.Record
import kotlinx.coroutines.flow.Flow

interface RecordUseCase {
    fun getRecords(idPatient: String): Flow<Resource<List<Record>>>
    fun getRecordDetail(id: String): Flow<Resource<Record>>
    fun insertRecord(record: Record)
    fun updateRecord(record: Record)
    fun deleteRecord(id: String)
}