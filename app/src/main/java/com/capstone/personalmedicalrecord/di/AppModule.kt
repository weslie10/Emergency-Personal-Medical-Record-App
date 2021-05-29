package com.capstone.personalmedicalrecord.di

import com.capstone.personalmedicalrecord.core.domain.usecase.*
import com.capstone.personalmedicalrecord.ui.login.LoginViewModel
import com.capstone.personalmedicalrecord.ui.patient.data.notes.NoteAddUpdateViewModel
import com.capstone.personalmedicalrecord.ui.patient.data.notes.NotesViewModel
import com.capstone.personalmedicalrecord.ui.patient.data.records.RecordAddUpdateViewModel
import com.capstone.personalmedicalrecord.ui.patient.data.records.RecordsViewModel
import com.capstone.personalmedicalrecord.ui.patient.profile.UpdatePatientViewModel
import com.capstone.personalmedicalrecord.ui.signup.SignUpViewModel
import com.capstone.personalmedicalrecord.ui.staff.profile.UpdateStaffViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<NoteUseCase> { NoteInteractor(get()) }
    factory<PatientUseCase> { PatientInteractor(get()) }
    factory<RecordUseCase> { RecordInteractor(get()) }
    factory<StaffUseCase> { StaffInteractor(get()) }
}

val viewModelModule = module {
    viewModel { NotesViewModel(get()) }
    viewModel { NoteAddUpdateViewModel(get()) }
    viewModel { RecordsViewModel(get()) }
    viewModel { RecordAddUpdateViewModel(get()) }
    viewModel { UpdatePatientViewModel(get()) }
    viewModel { UpdateStaffViewModel(get()) }
    viewModel { SignUpViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get()) }
}