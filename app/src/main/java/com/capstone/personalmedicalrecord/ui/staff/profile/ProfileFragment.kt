package com.capstone.personalmedicalrecord.ui.staff.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.databinding.FragmentStaffProfileBinding
import com.capstone.personalmedicalrecord.ui.login.LoginActivity
import com.capstone.personalmedicalrecord.utils.Utility.convertEmpty
import com.capstone.personalmedicalrecord.utils.Utility.navigateTo
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File


class ProfileFragment : Fragment() {
    private lateinit var preference: MyPreference
    private val profileViewModel: ProfileViewModel by viewModel()
    private var _binding: FragmentStaffProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStaffProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preference = MyPreference(requireActivity())
//        val textView: TextView = binding.textNotifications
//        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        profileViewModel.getStaff(preference.getId()).observe(viewLifecycleOwner, {
            if (it != null) {
                with(it) {
                    binding.fullName.text = name.convertEmpty()
                    binding.email.text = email.convertEmpty()
                    binding.phoneNumber.text = phoneNumber.convertEmpty()
                    binding.hospital.text = hospital.convertEmpty()

                    Glide.with(requireContext())
                        .load(File(picture))
                        .centerCrop()
                        .placeholder(R.drawable.user)
                        .error(R.drawable.user)
                        .into(binding.avatar)
                }
            }
        })

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