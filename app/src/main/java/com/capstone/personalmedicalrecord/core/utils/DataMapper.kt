package com.capstone.personalmedicalrecord.core.utils

import com.capstone.personalmedicalrecord.core.data.source.local.entity.NoteEntity
import com.capstone.personalmedicalrecord.core.data.source.local.entity.PatientEntity
import com.capstone.personalmedicalrecord.core.data.source.local.entity.RecordEntity
import com.capstone.personalmedicalrecord.core.data.source.local.entity.StaffEntity
import com.capstone.personalmedicalrecord.core.data.source.remote.response.NoteResponse
import com.capstone.personalmedicalrecord.core.data.source.remote.response.PatientResponse
import com.capstone.personalmedicalrecord.core.data.source.remote.response.RecordResponse
import com.capstone.personalmedicalrecord.core.data.source.remote.response.StaffResponse
import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.core.domain.model.Patient
import com.capstone.personalmedicalrecord.core.domain.model.Record
import com.capstone.personalmedicalrecord.core.domain.model.Staff

object DataMapper {

    fun mapNoteToEntity(input: Note) = NoteEntity(
        id = input.id,
        description = input.description,
        datetime = input.datetime,
        from = input.from,
        idPatient = input.idPatient
    )

    fun mapNoteToResponse(input: Note) = NoteResponse(
        id = input.id,
        description = input.description,
        datetime = input.datetime,
        from = input.from,
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
        from = input.from,
        idPatient = input.idPatient
    )

    fun mapNoteResponseToEntity(input: NoteResponse) = NoteEntity(
        id = input.id,
        datetime = input.datetime,
        description = input.description,
        from = input.from,
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
        bloodType = input.bloodType,
        picture = input.picture,
        term = input.term
    )

    fun mapPatientToResponse(input: Patient) = PatientResponse(
        id = input.id,
        name = input.name,
        email = input.email,
        password = input.password,
        phoneNumber = input.phoneNumber,
        dateBirth = input.dateBirth,
        address = input.address,
        gender = input.gender,
        bloodType = input.bloodType,
        picture = input.picture,
        term = input.term
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
        bloodType = input.bloodType,
        picture = input.picture,
        term = input.term
    )

    fun mapPatientResponseToEntity(input: PatientResponse) = PatientEntity(
        id = input.id,
        name = input.name,
        email = input.email,
        password = input.password,
        phoneNumber = input.phoneNumber,
        dateBirth = input.dateBirth,
        address = input.address,
        gender = input.gender,
        bloodType = input.bloodType,
        picture = input.picture,
        term = input.term
    )

    fun mapRecordToEntity(input: Record) = RecordEntity(
        id = input.id,
        date = input.date,
        haematocrit = input.haematocrit,
        haemoglobin = input.haemoglobin,
        erythrocyte = input.erythrocyte,
        leucocyte = input.leucocyte,
        thrombocyte = input.thrombocyte,
        mch = input.mch,
        mchc = input.mchc,
        mcv = input.mcv,
        idPatient = input.idPatient,
        treatment = input.treatment,
    )

    fun mapRecordToResponse(input: Record) = RecordResponse(
        id = input.id,
        date = input.date,
        haematocrit = input.haematocrit,
        haemoglobin = input.haemoglobin,
        erythrocyte = input.erythrocyte,
        leucocyte = input.leucocyte,
        thrombocyte = input.thrombocyte,
        mch = input.mch,
        mchc = input.mchc,
        mcv = input.mcv,
        idPatient = input.idPatient,
        treatment = input.treatment,
    )

    fun mapRecordEntitiesToDomain(input: List<RecordEntity>): List<Record> =
        input.map {
            mapRecordEntityToDomain(it)
        }

    fun mapRecordEntityToDomain(input: RecordEntity) = Record(
        id = input.id,
        date = input.date,
        haematocrit = input.haematocrit,
        haemoglobin = input.haemoglobin,
        erythrocyte = input.erythrocyte,
        leucocyte = input.leucocyte,
        thrombocyte = input.thrombocyte,
        mch = input.mch,
        mchc = input.mchc,
        mcv = input.mcv,
        idPatient = input.idPatient,
        treatment = input.treatment,
    )

    fun mapRecordResponseToEntity(input: RecordResponse) = RecordEntity(
        id = input.id,
        date = input.date,
        haematocrit = input.haematocrit,
        haemoglobin = input.haemoglobin,
        erythrocyte = input.erythrocyte,
        leucocyte = input.leucocyte,
        thrombocyte = input.thrombocyte,
        mch = input.mch,
        mchc = input.mchc,
        mcv = input.mcv,
        idPatient = input.idPatient,
        treatment = input.treatment,
    )

    fun mapStaffToEntity(input: Staff) = StaffEntity(
        id = input.id,
        name = input.name,
        email = input.email,
        password = input.password,
        phoneNumber = input.phoneNumber,
        hospital = input.hospital,
        picture = input.picture
    )

    fun mapStaffToResponse(input: Staff) = StaffResponse(
        id = input.id,
        name = input.name,
        email = input.email,
        password = input.password,
        phoneNumber = input.phoneNumber,
        hospital = input.hospital,
        picture = input.picture
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
        hospital = input.hospital,
        picture = input.picture
    )

    fun mapStaffResponseToEntity(input: StaffResponse) = StaffEntity(
        id = input.id,
        name = input.name,
        email = input.email,
        password = input.password,
        phoneNumber = input.phoneNumber,
        hospital = input.hospital,
        picture = input.picture
    )
}