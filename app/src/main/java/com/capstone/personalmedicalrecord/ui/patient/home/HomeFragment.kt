package com.capstone.personalmedicalrecord.ui.patient.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.core.data.Resource
import com.capstone.personalmedicalrecord.databinding.FragmentPatientHomeBinding
import com.capstone.personalmedicalrecord.utils.Utility.dateNow
import com.capstone.personalmedicalrecord.utils.Utility.setImage
import com.capstone.personalmedicalrecord.utils.Utility.simpleText
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File

class HomeFragment : Fragment() {
    private lateinit var preference: MyPreference
    private val homeViewModel: HomeViewModel by viewModel()

    private var _binding: FragmentPatientHomeBinding? = null
    private val binding get() = _binding as FragmentPatientHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPatientHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preference = MyPreference(requireActivity())

        homeViewModel.getPatient(preference.getId()).observe(viewLifecycleOwner, {
            binding.date.dateNow()
            if (it.data != null) {
                Log.d("homeFragment", it.data.toString())
                val patient = it.data
                val arr = patient.name.split(" ").toMutableList()
                val text = arr.simpleText()
                binding.greeting.text = resources.getString(R.string.greeting, text)

                binding.avatar.setImage(patient.picture)
            }

        })
        homeViewModel.getRecords(preference.getId()).observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> binding.records.text = String.format("Wait")
                    is Resource.Success -> {
                        binding.records.text = it.data?.size.toString()
                    }
                    is Resource.Error -> {
                        binding.records.text = String.format("Error")
                    }
                }
            }
        })
        homeViewModel.getNotes(preference.getId()).observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> binding.notes.text = String.format("Wait")
                    is Resource.Success -> {
                        binding.notes.text = it.data?.size.toString()
                    }
                    is Resource.Error -> {
                        binding.notes.text = String.format("Error")
                    }
                }
            }
        })
        binding.graph.addView(TestChart(requireContext()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}