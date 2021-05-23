package com.capstone.personalmedicalrecord.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.databinding.FragmentUpdateProfileBinding

class UpdateProfileFragment : Fragment() {

    private lateinit var preference: MyPreference
    private lateinit var notificationsViewModel: ProfileViewModel
    private var _binding: FragmentUpdateProfileBinding? = null
    private val binding get() = _binding as FragmentUpdateProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveChangesBtn.setOnClickListener {
            saveChanges()
        }
    }

    private fun saveChanges() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}