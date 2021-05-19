package com.capstone.personalmedicalrecord.ui.data.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.databinding.FragmentNotesBinding
import com.capstone.personalmedicalrecord.ui.data.DataViewModel
import com.capstone.personalmedicalrecord.ui.data.DetailDataFragment
import com.capstone.personalmedicalrecord.ui.profile.CheckIdFragment
import com.capstone.personalmedicalrecord.utils.DataDummy
import com.capstone.personalmedicalrecord.utils.Utility.navigateTo

class NotesFragment : Fragment(), NotesCallback {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding as FragmentNotesBinding
    private lateinit var viewModel: DataViewModel
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)

        if (activity != null) {
            notesAdapter = NotesAdapter(this)
            notesAdapter.setData(DataDummy.generateDummyNotes())
            with(binding.rvNotes) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = notesAdapter
            }

            binding.plusBtn.setOnClickListener {
                activity?.navigateTo(AddOrUpdateNotesFragment(),R.id.frame)
            }
        }
    }

    override fun onItemClick(note: Note) {
        viewModel.setType("notes")
        activity?.navigateTo(DetailDataFragment(),R.id.frame)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}