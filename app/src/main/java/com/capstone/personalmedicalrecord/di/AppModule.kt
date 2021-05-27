package com.capstone.personalmedicalrecord.di

import com.capstone.personalmedicalrecord.core.domain.usecase.*
import com.capstone.personalmedicalrecord.ui.patient.data.notes.NotesViewModel
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
}