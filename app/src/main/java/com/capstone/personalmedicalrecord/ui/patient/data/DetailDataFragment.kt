package com.capstone.personalmedicalrecord.ui.patient.data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.databinding.FragmentPatientDetailDataBinding
import com.capstone.personalmedicalrecord.ui.patient.data.notes.AddOrUpdateNotesFragment
import com.capstone.personalmedicalrecord.ui.patient.data.notes.NotesViewModel
import com.capstone.personalmedicalrecord.utils.Utility.clickBack
import com.capstone.personalmedicalrecord.utils.Utility.navigateTo
import org.koin.android.viewmodel.ext.android.viewModel

class DetailDataFragment : Fragment() {
    private var _binding: FragmentPatientDetailDataBinding? = null
    private val binding get() = _binding as FragmentPatientDetailDataBinding

    private val notesViewModel: NotesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
                val id = arg.getInt("id")
                notesViewModel.getNote(id).observe(viewLifecycleOwner, { note ->
                    binding.notes.visibility = View.VISIBLE
                    binding.detailNotesDate.text = note.datetime
                    binding.detailNotesDescription.text = note.description
                })
                activity?.clickBack(binding.backNotesBtn)

                binding.editNotesBtn.setOnClickListener {
                    val fragment = AddOrUpdateNotesFragment()
                    val bundle = Bundle().apply {
                        putInt("id", id)
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
                binding.records.visibility = View.VISIBLE
                activity?.clickBack(binding.backRecordsBtn)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}