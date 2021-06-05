package com.capstone.personalmedicalrecord.di

import com.capstone.personalmedicalrecord.core.domain.usecase.*
import com.capstone.personalmedicalrecord.ui.login.LoginViewModel
import com.capstone.personalmedicalrecord.ui.patient.data.notes.NoteAddUpdateViewModel
import com.capstone.personalmedicalrecord.ui.patient.data.notes.NotesViewModel
import com.capstone.personalmedicalrecord.ui.patient.data.records.RecordAddUpdateViewModel
import com.capstone.personalmedicalrecord.ui.patient.data.records.RecordsViewModel
import com.capstone.personalmedicalrecord.ui.patient.home.HomeViewModel
import com.capstone.personalmedicalrecord.ui.patient.profile.ProfileViewModel
import com.capstone.personalmedicalrecord.ui.patient.profile.UpdatePatientViewModel
import com.capstone.personalmedicalrecord.ui.signup.SignUpViewModel
import com.capstone.personalmedicalrecord.ui.staff.profile.UpdateStaffViewModel
import com.capstone.personalmedicalrecord.ui.staff.scanner.ScannerViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<NoteUseCase> { NoteInteractor(get()) }
    factory<PatientUseCase> { PatientInteractor(get()) }
    factory<RecordUseCase> { RecordInteractor(get()) }
    factory<StaffUseCase> { StaffInteractor(get()) }
}

val viewModelModule = module {
    //notes
    viewModel { NotesViewModel(get()) }
    viewModel { NoteAddUpdateViewModel(get()) }

    //records
    viewModel { RecordsViewModel(get(), get()) }
    viewModel { RecordAddUpdateViewModel(get()) }

    //patient
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { UpdatePatientViewModel(get()) }

    //staff
    viewModel { com.capstone.personalmedicalrecord.ui.staff.profile.ProfileViewModel(get()) }
    viewModel { UpdateStaffViewModel(get()) }
    viewModel { ScannerViewModel(get(), get()) }

    //auth
    viewModel { SignUpViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get()) }
}