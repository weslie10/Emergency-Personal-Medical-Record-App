package com.capstone.personalmedicalrecord.ui.staff.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.core.domain.model.Staff
import com.capstone.personalmedicalrecord.databinding.FragmentStaffUpdateProfileBinding
import com.capstone.personalmedicalrecord.utils.DataDummy
import com.capstone.personalmedicalrecord.utils.Utility.clickBack
import com.capstone.personalmedicalrecord.utils.Utility.searchStaff

class UpdateProfileFragment : Fragment() {

    private lateinit var preference: MyPreference
    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentStaffUpdateProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStaffUpdateProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preference = MyPreference(requireContext())

        val staff = preference.getId().searchStaff()
        with(staff) {
            binding.inputFullName.setText(name)
            binding.inputEmail.setText(email)
            binding.inputPhoneNumber.setText(phoneNumber)
            binding.inputHospital.setText(hospital)
        }

        binding.saveChangesBtn.setOnClickListener {
            val id = DataDummy.listStaff.indexOf(staff)
            DataDummy.listStaff[id] = Staff(
                preference.getId(),
                binding.inputFullName.text.toString(),
                binding.inputEmail.text.toString(),
                staff.password,
                binding.inputPhoneNumber.text.toString(),
                binding.inputHospital.text.toString()
            )
            activity?.supportFragmentManager?.popBackStack()
        }

        activity?.clickBack(binding.backBtn)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}