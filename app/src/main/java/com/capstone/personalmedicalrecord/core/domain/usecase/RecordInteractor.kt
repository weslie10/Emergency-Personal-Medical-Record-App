package com.capstone.personalmedicalrecord.core.domain.usecase

import com.capstone.personalmedicalrecord.core.domain.model.Record
import com.capstone.personalmedicalrecord.core.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow

class RecordInteractor(private val repository: IRepository) : RecordUseCase {
    override fun getRecords(idPatient: String) = repository.getRecords(idPatient)

    override fun getRecordDetail(id: String) = repository.getRecordDetail(id)

    override fun insertRecord(record: Record) = repository.insertRecord(record)

    override fun updateRecord(record: Record) = repository.updateRecord(record)

    override fun deleteRecord(id: String) = repository.deleteRecord(id)
}