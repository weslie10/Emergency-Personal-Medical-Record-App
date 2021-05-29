package com.capstone.personalmedicalrecord.ui.patient.data.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.databinding.FragmentAddOrUpdateNotesBinding
import com.capstone.personalmedicalrecord.utils.DataDummy
import com.capstone.personalmedicalrecord.utils.Utility.clickBack
import com.capstone.personalmedicalrecord.utils.Utility.getDate
import com.capstone.personalmedicalrecord.utils.Utility.searchNote
import org.koin.android.viewmodel.ext.android.viewModel

class AddOrUpdateNotesFragment : Fragment() {
    private var _binding: FragmentAddOrUpdateNotesBinding? = null
    private val binding get() = _binding as FragmentAddOrUpdateNotesBinding

    //    private lateinit var notesViewModel: NotesViewModel
    private val viewModel: NoteAddUpdateViewModel by viewModel()
    private lateinit var preference: MyPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddOrUpdateNotesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preference = MyPreference(requireContext())


        val arg = this.arguments
        if (arg != null) {
            when (arg.getString("state")) {
                "Add" -> {
                    addNote()
                }
                "Update" -> {
                    updateNote(arg.getInt("id"))
                }
            }
        }
        activity?.clickBack(binding.backBtn)

//        notesViewModel = ViewModelProvider(requireActivity()).get(NotesViewModel::class.java)
//
//        notesViewModel.getState().observe(viewLifecycleOwner, {
//            when (it) {
//                "Add" -> {
//                    addNote()
//                }
//                "Update" -> {
//                    updateNote()
//                }
//            }
//        })
    }

    private fun addNote() {
        binding.notesBtn.apply {
            text = resources.getString(R.string.add_note)
            setOnClickListener {
                DataDummy.listNotes.add(
                    Note(
                        datetime = getDate(),
                        description = binding.inputNote.text.toString(),
                        idPatient = preference.getId()
                    )
                )
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }

    private fun updateNote(id: Int) {
        val note = id.searchNote()
        binding.inputNote.setText(note.description)
        binding.notesBtn.apply {
            text = resources.getString(R.string.update_note)
            setOnClickListener {
                val idx = DataDummy.listNotes.indexOf(note)
                DataDummy.listNotes[idx] = Note(
                    note.id,
                    datetime = getDate(),
                    description = binding.inputNote.text.toString(),
                    idPatient = preference.getId()
                )
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }
}
