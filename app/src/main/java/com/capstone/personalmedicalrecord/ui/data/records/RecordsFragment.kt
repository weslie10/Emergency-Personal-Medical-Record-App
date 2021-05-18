package com.capstone.personalmedicalrecord.ui.data.records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.personalmedicalrecord.core.domain.model.Record
import com.capstone.personalmedicalrecord.databinding.FragmentRecordsBinding
import com.capstone.personalmedicalrecord.utils.DataDummy

class RecordsFragment : Fragment(), RecordsCallback {

    private var _binding: FragmentRecordsBinding? = null
    private val binding get() = _binding as FragmentRecordsBinding
//    private val viewModel: RecordsViewModel by viewModels()
    private lateinit var recordsAdapter: RecordsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            recordsAdapter = RecordsAdapter(this)
            recordsAdapter.setData(DataDummy.generateDummyRecords())
            with(binding.rvRecords) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = recordsAdapter
            }
        }
    }

    override fun onItemClick(record: Record) {
        Toast.makeText(requireActivity(), "This item is clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}