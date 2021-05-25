package com.capstone.personalmedicalrecord.ui.patient.data.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.databinding.FragmentAddOrUpdateNotesBinding
import com.capstone.personalmedicalrecord.utils.DataDummy
import com.capstone.personalmedicalrecord.utils.Utility.clickBack
import com.capstone.personalmedicalrecord.utils.Utility.getDate
import com.capstone.personalmedicalrecord.utils.Utility.searchNote

class AddOrUpdateNotesFragment : Fragment() {
    private var _binding: FragmentAddOrUpdateNotesBinding? = null
    private val binding get() = _binding as FragmentAddOrUpdateNotesBinding
    private lateinit var notesViewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddOrUpdateNotesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesViewModel = ViewModelProvider(requireActivity()).get(NotesViewModel::class.java)

        notesViewModel.getState().observe(viewLifecycleOwner, {
            when (it) {
                "Add" -> {
                    addNote()
                }
                "Update" -> {
                    updateNote()
                }
            }
        })
        activity?.clickBack(binding.backBtn)
    }

    private fun addNote() {
        binding.notesBtn.apply {
            text = resources.getString(R.string.add_note)
            setOnClickListener {
                DataDummy.listNotes.add(
                    Note(
                        DataDummy.listNotes.size + 1,
                        getDate(),
                        binding.inputNote.text.toString()
                    )
                )
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }

    private fun updateNote() {
        val note = notesViewModel.getId().searchNote()
        binding.inputNote.setText(note.description)
        binding.notesBtn.apply {
            text = resources.getString(R.string.update_note)
            setOnClickListener {
                val idx = DataDummy.listNotes.indexOf(note)
                DataDummy.listNotes[idx] = Note(
                    note.id,
                    getDate(),
                    binding.inputNote.text.toString()
                )
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }
}
