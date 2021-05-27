package com.capstone.personalmedicalrecord.ui.patient.data.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.databinding.FragmentNotesBinding
import com.capstone.personalmedicalrecord.ui.patient.data.DataViewModel
import com.capstone.personalmedicalrecord.ui.patient.data.DetailDataFragment
import com.capstone.personalmedicalrecord.utils.DataDummy
import com.capstone.personalmedicalrecord.utils.Utility.navigateTo
import org.koin.android.viewmodel.ext.android.viewModel

class NotesFragment : Fragment(), NotesCallback {
    private lateinit var dataViewModel: DataViewModel
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var preference: MyPreference
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding as FragmentNotesBinding
    private val notesViewModel: NotesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preference = MyPreference(requireActivity())
        dataViewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)

        if (activity != null) {
            notesAdapter = NotesAdapter(this)
            val filter = DataDummy.listNotes.filter { note -> note.idPatient == preference.getId() }
            notesAdapter.setData(filter)
            with(binding.rvNotes) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = notesAdapter
            }

            binding.plusBtn.setOnClickListener {
                notesViewModel.setState("Add")
                activity?.navigateTo(AddOrUpdateNotesFragment(), R.id.frame)
            }
        }
    }

    override fun onItemClick(note: Note) {
        dataViewModel.setType("notes")
        notesViewModel.setId(note.id)
        activity?.navigateTo(DetailDataFragment(), R.id.frame)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
