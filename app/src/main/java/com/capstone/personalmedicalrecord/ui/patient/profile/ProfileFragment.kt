package com.capstone.personalmedicalrecord.ui.patient.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.databinding.FragmentPatientProfileBinding
import com.capstone.personalmedicalrecord.ui.login.LoginActivity
import com.capstone.personalmedicalrecord.utils.Utility.convertEmpty
import com.capstone.personalmedicalrecord.utils.Utility.navigateTo
import org.koin.android.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment() {
    private lateinit var preference: MyPreference
    private val profileViewModel: ProfileViewModel by viewModel()

    private var _binding: FragmentPatientProfileBinding? = null
    private val binding get() = _binding as FragmentPatientProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPatientProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preference = MyPreference(requireActivity())

        profileViewModel.getPatient(preference.getId()).observe(viewLifecycleOwner, {
            if (it != null) {
                with(it) {
                    binding.fullName.text = name.convertEmpty()
                    binding.email.text = email.convertEmpty()
                    binding.address.text = address.convertEmpty()
                    binding.phoneNumber.text = phoneNumber.convertEmpty()
                    binding.dateBirth.text = dateBirth.convertEmpty()
                    binding.gender.text = gender.convertEmpty()
                    binding.bloodType.text = bloodType.convertEmpty()
                }
            }
        })

        binding.checkIdBtn.setOnClickListener {
            activity?.navigateTo(CheckIdFragment(), R.id.frame)
        }

        binding.logoutBtn.setOnClickListener {
            preference.setId(0)
            preference.setRole("")
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        binding.editProfileBtn.setOnClickListener {
            activity?.navigateTo(UpdateProfileFragment(), R.id.frame)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}