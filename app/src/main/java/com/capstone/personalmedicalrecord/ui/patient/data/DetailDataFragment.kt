package com.capstone.personalmedicalrecord.ui.patient.data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.core.data.Resource
import com.capstone.personalmedicalrecord.databinding.FragmentPatientDetailDataBinding
import com.capstone.personalmedicalrecord.ui.patient.data.notes.AddOrUpdateNotesFragment
import com.capstone.personalmedicalrecord.ui.patient.data.notes.NotesViewModel
import com.capstone.personalmedicalrecord.ui.patient.data.records.AddOrUpdateRecordFragment
import com.capstone.personalmedicalrecord.ui.patient.data.records.RecordsViewModel
import com.capstone.personalmedicalrecord.utils.Utility.clickBack
import com.capstone.personalmedicalrecord.utils.Utility.navigateTo
import org.koin.android.viewmodel.ext.android.viewModel

class DetailDataFragment : Fragment() {
    private var _binding: FragmentPatientDetailDataBinding? = null
    private val binding get() = _binding as FragmentPatientDetailDataBinding

    private val notesViewModel: NotesViewModel by viewModel()
    private val recordsViewModel: RecordsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPatientDetailDataBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arg = this.arguments
        if (arg != null) {
            val type = arg.getString("type")
            binding.notes.visibility = View.GONE
            binding.records.visibility = View.GONE
            if (type == "notes") {
                val id = arg.getString("id").toString()
                notesViewModel.getNoteDetail(id).observe(viewLifecycleOwner, { note ->
                    if (note != null) {
                        when (note) {
                            is Resource.Loading -> {
                            }
                            is Resource.Success -> {
                                binding.notes.visibility = View.VISIBLE
                                binding.detailNotesDate.text = note.data?.datetime
                                binding.detailNotesDescription.text = note.data?.description
                                binding.detailNotesFrom.text =
                                    String.format("From %s", note.data?.from)
                            }
                            is Resource.Error -> {
                            }
                        }
                    }
                })
                activity?.clickBack(binding.backNotesBtn)

                binding.editNotesBtn.setOnClickListener {
                    val fragment = AddOrUpdateNotesFragment()
                    val bundle = Bundle().apply {
                        putString("id", id)
                        putString("state", "Update")
                    }
                    fragment.arguments = bundle
                    activity?.apply {
                        supportFragmentManager.popBackStack()
                        navigateTo(fragment, R.id.frame)
                    }
                }

                binding.deleteNotesBtn.setOnClickListener {
                    notesViewModel.deleteNote(id)
                    activity?.supportFragmentManager?.popBackStack()
                }
            } else {
                val id = arg.getString("id").toString()
                recordsViewModel.getRecordDetail(id).observe(viewLifecycleOwner, { record ->
                    if (record != null) {
                        when (record) {
                            is Resource.Loading -> {
                            }
                            is Resource.Success -> {
                                binding.records.visibility = View.VISIBLE
                                binding.detailRecordsDate.text = record.data?.date
                                binding.detailRecordsHaematocrit.text =
                                    String.format("%s\n", record.data?.haematocrit.toString())
                                binding.detailRecordsHaemoglobin.text =
                                    String.format("%s\n", record.data?.haemoglobin.toString())
                                binding.detailRecordsErythrocyte.text =
                                    String.format("%s\n", record.data?.erythrocyte.toString())
                                binding.detailRecordsLeucocyte.text =
                                    record.data?.leucocyte.toString()
                                binding.detailRecordsThrombocyte.text =
                                    record.data?.thrombocyte.toString()
                                binding.detailRecordsMch.text = record.data?.mch.toString()
                                binding.detailRecordsMchc.text = record.data?.mchc.toString()
                                binding.detailRecordsMcv.text = record.data?.mcv.toString()
                            }
                            is Resource.Error -> {
                            }
                        }
                    }
                })
                activity?.clickBack(binding.backRecordsBtn)

                binding.editRecordsBtn.setOnClickListener {
                    val fragment = AddOrUpdateRecordFragment()
                    val bundle = Bundle().apply {
                        putString("id", id)
                        putString("state", "Update")
                    }
                    fragment.arguments = bundle
                    activity?.apply {
                        supportFragmentManager.popBackStack()
                        navigateTo(fragment, R.id.frame)
                    }
                }

                binding.deleteRecordsBtn.setOnClickListener {
                    recordsViewModel.deleteRecord(id)
                    activity?.supportFragmentManager?.popBackStack()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}