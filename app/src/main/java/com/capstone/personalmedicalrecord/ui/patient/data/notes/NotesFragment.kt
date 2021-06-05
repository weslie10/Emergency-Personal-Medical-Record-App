package com.capstone.personalmedicalrecord.ui.patient.data.notes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.core.data.Resource
import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.databinding.FragmentNotesBinding
import com.capstone.personalmedicalrecord.ui.patient.data.DetailDataFragment
import com.capstone.personalmedicalrecord.utils.Utility.navigateTo
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class NotesFragment : Fragment(), NotesCallback {
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var preference: MyPreference
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding as FragmentNotesBinding
    private val viewModel: NotesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
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
                Log.d("notes", notes.data.toString())
                if (notes != null) {
                    when (notes) {
                        is Resource.Loading -> showLoading(true)
                        is Resource.Success -> {
                            showLoading(false)
                            notesAdapter.setData(notes.data)
                            showEmpty(notes.data?.isEmpty() as Boolean)
                        }
                        is Resource.Error -> {
                            showLoading(false)
                            Snackbar.make(
                                binding.root,
                                "There's some mistake",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
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

    private fun showEmpty(isEmpty: Boolean) {
        if (isEmpty) {
            binding.empty.visibility = View.VISIBLE
        } else {
            binding.empty.visibility = View.INVISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvNotes.visibility = View.INVISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.rvNotes.visibility = View.VISIBLE
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
