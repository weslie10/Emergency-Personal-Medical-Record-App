package com.capstone.personalmedicalrecord.ui.patient.data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.personalmedicalrecord.databinding.FragmentPatientDetailDataBinding
import com.capstone.personalmedicalrecord.utils.Utility.clickBack

class DetailDataFragment : Fragment() {
    private var _binding: FragmentPatientDetailDataBinding? = null
    private val binding get() = _binding as FragmentPatientDetailDataBinding
    private lateinit var viewModel: DataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPatientDetailDataBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)

        viewModel.getType().observe(viewLifecycleOwner, { type ->
            binding.notes.visibility = View.GONE
            binding.records.visibility = View.GONE
            if (type == "notes") {
                binding.notes.visibility = View.VISIBLE
                activity?.clickBack(binding.backNotesBtn)
            } else {
                binding.records.visibility = View.VISIBLE
                activity?.clickBack(binding.backRecordsBtn)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}