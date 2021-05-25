package com.capstone.personalmedicalrecord.ui.patient.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.databinding.FragmentPatientHomeBinding
import com.capstone.personalmedicalrecord.utils.DataDummy
import com.capstone.personalmedicalrecord.utils.Utility.dateNow
import com.capstone.personalmedicalrecord.utils.Utility.searchPatient
import com.capstone.personalmedicalrecord.utils.Utility.simpleText

class HomeFragment : Fragment() {
    private lateinit var preference: MyPreference
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentPatientHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentPatientHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preference = MyPreference(requireActivity())

        val patient = preference.getId().searchPatient()
        val arr = patient.name.split(" ").toMutableList()
        val text = arr.simpleText()
        binding.greeting.text = resources.getString(R.string.greeting, text)
        binding.date.dateNow()
        binding.records.text = DataDummy.listRecords.size.toString()
        binding.notes.text = DataDummy.listNotes.size.toString()
        binding.graph.addView(TestChart(requireContext()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}