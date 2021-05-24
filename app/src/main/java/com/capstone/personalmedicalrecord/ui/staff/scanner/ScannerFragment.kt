package com.capstone.personalmedicalrecord.ui.staff.scanner

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.personalmedicalrecord.databinding.FragmentStaffScannerBinding
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class ScannerFragment : Fragment() {

    private lateinit var scannerViewModel: ScannerViewModel
    private var _binding: FragmentStaffScannerBinding? = null
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scanBtn.setOnClickListener {
            setUpScanner()
        }
    }

    private fun setUpScanner() {
        IntentIntegrator(requireActivity()).apply {
            setOrientationLocked(false)
            setPrompt("For Flash use volume up key")
            setBeepEnabled(true)
            initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result: IntentResult? =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents != null) {
                AlertDialog.Builder(context).apply {
                    setTitle("Result")
                    setMessage(result.contents)
                    setPositiveButton("ok") { dialog, _ ->
                        dialog.dismiss()
                    }
                    show()
                }
            } else {
                Toast.makeText(context, "There's no qr code", Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}