package com.capstone.personalmedicalrecord.ui.staff.scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.capstone.personalmedicalrecord.databinding.FragmentStaffScannerBinding

class ScannerFragment : Fragment() {

    private lateinit var scannerViewModel: ScannerViewModel
    private var _binding: FragmentStaffScannerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        scannerViewModel =
            ViewModelProvider(this).get(ScannerViewModel::class.java)

        _binding = FragmentStaffScannerBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        val textView: TextView = binding.textScanner
//        scannerViewModel.text.observe(viewLifecycleOwner, {
//            textView.text = it
//        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}