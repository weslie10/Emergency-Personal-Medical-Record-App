package com.capstone.personalmedicalrecord.ui.patient.data.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.databinding.FragmentNotesBinding
import com.capstone.personalmedicalrecord.ui.patient.data.DetailDataFragment
import com.capstone.personalmedicalrecord.utils.Utility.navigateTo
import org.koin.android.viewmodel.ext.android.viewModel

class NotesFragment : Fragment(), NotesCallback {
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var preference: MyPreference
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding as FragmentNotesBinding
    private val viewModel: NotesViewModel by viewModel()

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

        if (activity != null) {
            notesAdapter = NotesAdapter(this)
            val id = preference.getId()
            viewModel.getNotes(id).observe(viewLifecycleOwner, { notes ->
                if (notes!=null) {
                    notesAdapter.setData(notes)
                }
            })
            with(binding.rvNotes) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = notesAdapter
            }
            binding.plusBtn.setOnClickListener {
                val fragment = AddOrUpdateNotesFragment()
                val bundle = Bundle().apply {
                    putString("state", "Add")
                }
                fragment.arguments = bundle
                activity?.navigateTo(fragment, R.id.frame)
            }
        }
    }

    override fun onItemClick(note: Note) {
        val fragment = DetailDataFragment()
        val bundle = Bundle().apply {
            putString("id", note.id)
            putString("type", "notes")
        }
        fragment.arguments = bundle
        activity?.navigateTo(fragment, R.id.frame)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
