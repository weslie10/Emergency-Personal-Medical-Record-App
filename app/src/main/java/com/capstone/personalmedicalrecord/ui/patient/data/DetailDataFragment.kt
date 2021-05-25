package com.capstone.personalmedicalrecord.ui.patient.data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.databinding.FragmentPatientDetailDataBinding
import com.capstone.personalmedicalrecord.ui.patient.data.notes.AddOrUpdateNotesFragment
import com.capstone.personalmedicalrecord.ui.patient.data.notes.NotesViewModel
import com.capstone.personalmedicalrecord.utils.DataDummy
import com.capstone.personalmedicalrecord.utils.Utility.clickBack
import com.capstone.personalmedicalrecord.utils.Utility.navigateTo

class DetailDataFragment : Fragment() {
    private var _binding: FragmentPatientDetailDataBinding? = null
    private val binding get() = _binding as FragmentPatientDetailDataBinding
    private lateinit var dataViewModel: DataViewModel
    private lateinit var notesViewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPatientDetailDataBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataViewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        notesViewModel = ViewModelProvider(requireActivity()).get(NotesViewModel::class.java)

        dataViewModel.getType().observe(viewLifecycleOwner, { type ->
            binding.notes.visibility = View.GONE
            binding.records.visibility = View.GONE
            if (type == "notes") {
                val id = notesViewModel.getId()
                val note = DataDummy.listNotes.filter { note -> note.id == id }[0]
                binding.notes.visibility = View.VISIBLE
                binding.detailNotesDate.text = note.date
                binding.detailNotesDescription.text = note.description
                activity?.clickBack(binding.backNotesBtn)

                binding.editNotesBtn.setOnClickListener {
                    notesViewModel.setState("Update")
                    activity?.apply {
                        supportFragmentManager.popBackStack()
                        navigateTo(AddOrUpdateNotesFragment(), R.id.frame)
                    }
                }

                binding.deleteNotesBtn.setOnClickListener {
                    DataDummy.listNotes.removeAt(id - 1)
                    activity?.supportFragmentManager?.popBackStack()
                }
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