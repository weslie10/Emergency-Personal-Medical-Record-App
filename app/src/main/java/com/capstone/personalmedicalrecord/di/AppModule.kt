package com.capstone.personalmedicalrecord.di

import com.capstone.personalmedicalrecord.core.domain.usecase.NoteInteractor
import com.capstone.personalmedicalrecord.core.domain.usecase.NoteUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory<NoteUseCase> { NoteInteractor(get()) }
}

val viewModelModule = module {

}