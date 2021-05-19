package com.capstone.personalmedicalrecord.ui.data.records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.core.domain.model.Record
import com.capstone.personalmedicalrecord.databinding.FragmentRecordsBinding
import com.capstone.personalmedicalrecord.ui.data.DataViewModel
import com.capstone.personalmedicalrecord.ui.data.DetailDataFragment
import com.capstone.personalmedicalrecord.utils.DataDummy
import com.capstone.personalmedicalrecord.utils.Utility.navigateTo
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RecordsFragment : Fragment(), RecordsCallback {

    private var _binding: FragmentRecordsBinding? = null
    private val binding get() = _binding as FragmentRecordsBinding
    private lateinit var viewModel: DataViewModel
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

        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        if (activity != null) {
            recordsAdapter = RecordsAdapter(this)
            recordsAdapter.setData(DataDummy.generateDummyRecords())
            with(binding.rvRecords) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = recordsAdapter
            }

            binding.plusBtn.setOnClickListener {
                val singleItems = arrayOf("Take a Photo", "Choose a photo", "Choose a document")
                var checkedItem = 0

                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(getString(R.string.add_record_text))
                    .setNeutralButton(getString(R.string.cancel), null)
                    .setPositiveButton(getString(R.string.ok)) { _, _ ->
                        Toast.makeText(
                            context,
                            "You've selected: ${singleItems[checkedItem]}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .setSingleChoiceItems(singleItems, 0) { _, which ->
                        checkedItem = which
                    }
                    .show()
            }
        }
    }

    override fun onItemClick(record: Record) {
        viewModel.setType("records")
        activity?.navigateTo(DetailDataFragment(), R.id.frame)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}