package com.capstone.personalmedicalrecord.ui.patient.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.core.domain.model.Patient
import com.capstone.personalmedicalrecord.databinding.FragmentPatientUpdateProfileBinding
import com.capstone.personalmedicalrecord.utils.DataDummy
import com.capstone.personalmedicalrecord.utils.Utility.clickBack
import com.capstone.personalmedicalrecord.utils.Utility.searchPatient
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class UpdateProfileFragment : Fragment() {
    private lateinit var preference: MyPreference
    private var _binding: FragmentPatientUpdateProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPatientUpdateProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preference = MyPreference(requireActivity())

        val patient = preference.getId().searchPatient()
        with(patient) {
            binding.inputFullName.setText(name)
            binding.inputEmail.setText(email)
            binding.inputAddress.setText(address)
            binding.inputPhoneNumber.setText(phoneNumber)
            binding.inputDateBirth.setText(dateBirth)
            binding.inputGender.setText(gender)
            binding.inputBloodType.setText(bloodType)
        }

        binding.saveChangesBtn.setOnClickListener {
            val id = DataDummy.listPatient.indexOf(patient)
            DataDummy.listPatient[id] = Patient(
                preference.getId(),
                binding.inputFullName.text.toString(),
                binding.inputEmail.text.toString(),
                patient.password,
                binding.inputAddress.text.toString(),
                binding.inputPhoneNumber.text.toString(),
                binding.inputDateBirth.text.toString(),
                binding.inputGender.text.toString(),
                binding.inputBloodType.text.toString(),
            )
            activity?.supportFragmentManager?.popBackStack()
        }
        binding.changePhoto.setOnClickListener {
            val singleItems = arrayOf("Take a Photo", "Choose a photo")
            var checkedItem = 0

            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.add_record_text))
                .setNeutralButton(getString(R.string.cancel), null)
                .setPositiveButton(getString(R.string.ok)) { _, _ ->
//                    when (checkedItem) {
//                        0 -> takePhoto()
//                        1 -> choosePhoto()
//                    }
                    Log.d("check", singleItems[checkedItem])
                }
                .setSingleChoiceItems(singleItems, 0) { _, which ->
                    checkedItem = which
                }
                .show()
        }
        activity?.clickBack(binding.backBtn)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}