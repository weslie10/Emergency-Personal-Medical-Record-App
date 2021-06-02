package com.capstone.personalmedicalrecord.ui.staff.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.personalmedicalrecord.core.domain.usecase.StaffUseCase

class ProfileViewModel(private val staffUseCase: StaffUseCase) : ViewModel() {
    fun getStaff(id: String) = staffUseCase.getStaffDetail(id).asLiveData()
}