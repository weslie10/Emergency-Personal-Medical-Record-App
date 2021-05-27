package com.capstone.personalmedicalrecord.core.utils

import com.capstone.personalmedicalrecord.core.data.source.local.entity.NoteEntity
import com.capstone.personalmedicalrecord.core.data.source.local.entity.PatientEntity
import com.capstone.personalmedicalrecord.core.data.source.local.entity.RecordEntity
import com.capstone.personalmedicalrecord.core.data.source.local.entity.StaffEntity
import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.core.domain.model.Patient
import com.capstone.personalmedicalrecord.core.domain.model.Record
import com.capstone.personalmedicalrecord.core.domain.model.Staff

object DataMapper {

    fun mapNoteToEntity(input: Note) = NoteEntity(
        id = input.id,
        description = input.description,
        datetime = input.datetime,
        idPatient = input.idPatient
    )

    fun mapNoteEntitiesToDomain(input: List<NoteEntity>): List<Note> =
        input.map {
            mapNoteEntityToDomain(it)
        }

    fun mapNoteEntityToDomain(input: NoteEntity) = Note(
        id = input.id,
        description = input.description,
        datetime = input.datetime,
        idPatient = input.idPatient
    )

    fun mapPatientToEntity(input: Patient) = PatientEntity(
        id = input.id,
        name = input.name,
        email = input.email,
        password = input.password,
        phoneNumber = input.phoneNumber,
        dateBirth = input.dateBirth,
        address = input.address,
        gender = input.gender,
        bloodType = input.bloodType
    )

    fun mapPatientEntitiesToDomain(input: List<PatientEntity>): List<Patient> =
        input.map {
            mapPatientEntityToDomain(it)
        }

    fun mapPatientEntityToDomain(input: PatientEntity) = Patient(
        id = input.id,
        name = input.name,
        email = input.email,
        password = input.password,
        phoneNumber = input.phoneNumber,
        dateBirth = input.dateBirth,
        address = input.address,
        gender = input.gender,
        bloodType = input.bloodType
    )

    fun mapRecordToEntity(input: Record) = RecordEntity(
        id = input.id,
        datetime = input.datetime,
        description = input.description,
        place = input.place,
        idPatient = input.idPatient
    )

    fun mapRecordEntitiesToDomain(input: List<RecordEntity>): List<Record> =
        input.map {
            mapRecordEntityToDomain(it)
        }

    fun mapRecordEntityToDomain(input: RecordEntity) = Record(
        id = input.id,
        datetime = input.datetime,
        description = input.description,
        place = input.place,
        idPatient = input.idPatient
    )

    fun mapStaffToEntity(input: Staff) = StaffEntity(
        id = input.id,
        name = input.name,
        email = input.email,
        password = input.password,
        phoneNumber = input.phoneNumber,
        hospital = input.hospital
    )

    fun mapStaffEntitiesToDomain(input: List<StaffEntity>): List<Staff> =
        input.map {
            mapStaffEntityToDomain(it)
        }

    fun mapStaffEntityToDomain(input: StaffEntity) = Staff(
        id = input.id,
        name = input.name,
        email = input.email,
        password = input.password,
        phoneNumber = input.phoneNumber,
        hospital = input.hospital
    )
}