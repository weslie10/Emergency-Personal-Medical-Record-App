package com.capstone.personalmedicalrecord.ui.patient.data.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.core.data.Resource
import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.databinding.FragmentAddOrUpdateNotesBinding
import com.capstone.personalmedicalrecord.utils.Utility.clickBack
import com.capstone.personalmedicalrecord.utils.Utility.getDatetime
import com.capstone.personalmedicalrecord.utils.Utility.hideKeyboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class AddOrUpdateNotesFragment : Fragment() {
    private var _binding: FragmentAddOrUpdateNotesBinding? = null
    private val binding get() = _binding as FragmentAddOrUpdateNotesBinding

    private val viewModel: NoteAddUpdateViewModel by viewModel()
    private lateinit var preference: MyPreference
    private lateinit var from: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
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
                    val id = arg.getString("id").toString()
                    viewModel.getNote(id).observe(viewLifecycleOwner, { note ->
                        if (note != null) {
                            when (note) {
                                is Resource.Loading -> {
                                }
                                is Resource.Success -> {
                                    binding.inputNote.setText(note.data?.description)
                                    from = note.data?.from.toString()
                                }
                                is Resource.Error -> {
                                }
                            }
                        }
                    })
                    updateNote(id)
                }
            }
        }
        activity?.clickBack(binding.backBtn)
    }

    private fun addNote() {
        binding.notesBtn.apply {
            text = resources.getString(R.string.add_note)
            setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.insert(
                        Note(
                            datetime = getDatetime(),
                            description = binding.inputNote.text.toString(),
                            from = "me",
                            idPatient = preference.getId(),
                        )
                    )
                }
                it.hideKeyboard()
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }

    private fun updateNote(id: String) {
        binding.notesBtn.apply {
            text = resources.getString(R.string.update_note)
            setOnClickListener {
                viewModel.update(
                    Note(
                        id = id,
                        datetime = getDatetime(),
                        description = binding.inputNote.text.toString(),
                        from = from,
                        idPatient = preference.getId()
                    )
                )
                it.hideKeyboard()
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }
}
