package com.capstone.personalmedicalrecord.ui.patient.data.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.personalmedicalrecord.databinding.FragmentAddOrUpdateNotesBinding
import com.capstone.personalmedicalrecord.utils.Utility.clickBack

class AddOrUpdateNotesFragment : Fragment() {
    private var _binding: FragmentAddOrUpdateNotesBinding? = null
    private val binding get() = _binding as FragmentAddOrUpdateNotesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddOrUpdateNotesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        activity?.clickBack(binding.backBtn)
    }
}