package com.capstone.personalmedicalrecord.ui.staff.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.core.domain.usecase.NoteUseCase
import com.capstone.personalmedicalrecord.core.domain.usecase.StaffUseCase

class ScannerViewModel(
    private val staffUseCase: StaffUseCase,
    private val noteUseCase: NoteUseCase,
) : ViewModel() {
    fun getStaff(id: String) = staffUseCase.getStaffDetail(id).asLiveData()
    fun insert(note: Note) = noteUseCase.insertNote(note)
}