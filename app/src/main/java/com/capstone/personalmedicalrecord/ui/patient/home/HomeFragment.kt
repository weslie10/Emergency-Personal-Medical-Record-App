package com.capstone.personalmedicalrecord.ui.patient.home

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.databinding.FragmentPatientHomeBinding
import com.capstone.personalmedicalrecord.utils.DataDummy
import com.capstone.personalmedicalrecord.utils.Utility.dateNow
import com.capstone.personalmedicalrecord.utils.Utility.simpleText
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File

class HomeFragment : Fragment() {
    private lateinit var preference: MyPreference
    private val homeViewModel: HomeViewModel by viewModel()

    private var _binding: FragmentPatientHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPatientHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preference = MyPreference(requireActivity())

        Log.d("test","hello")
        homeViewModel.getPatient(preference.getId()).observe(viewLifecycleOwner, { patient ->
            Log.d("test","hello1")
            if (patient != null) {
                Log.d("test","hello2")
                val arr = patient.name.split(" ").toMutableList()
                val text = arr.simpleText()
                binding.greeting.text = resources.getString(R.string.greeting, text)
                binding.date.dateNow()

                Glide.with(requireContext())
                    .load(File("/storage/emulated/0/Android/data/com.capstone.personalmedicalrecord/files/Pictures/JPEG_20210529_213333_2805315822923683355.jpg"))
                    .placeholder(R.drawable.user)
                    .error(R.drawable.user)
                    .centerCrop()
                    .into(binding.avatar)
//                "/storage/emulated/0/Android/data/com.capstone.personalmedicalrecord/files/Pictures/JPEG_20210529_213333_2805315822923683355.jpg"
//                if (patient.picture.length > 2) {
//                } else {
//                    Glide.with(requireContext())
//                        .load(R.drawable.user)
//                        .centerCrop()
//                        .into(binding.avatar)
//                }
            }
        })
        binding.records.text = DataDummy.listRecords.size.toString()
        binding.notes.text = DataDummy.listNotes.size.toString()
        binding.graph.addView(TestChart(requireContext()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}