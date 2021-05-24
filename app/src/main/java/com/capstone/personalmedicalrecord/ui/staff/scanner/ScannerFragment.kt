package com.capstone.personalmedicalrecord.ui.staff.scanner

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scanBtn.setOnClickListener {
            setUpScanner()
        }
    }

    private fun setUpScanner() {
        IntentIntegrator(activity).apply {
            setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            setCameraId(0)
            setOrientationLocked(false)
            setPrompt("scanning")
            setBeepEnabled(true)
            setBarcodeImageEnabled(true)
            initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result.contents != null) {
//            ClipboardManager manager =(ClipboardManager) getSystemService (CLIPBOARD_SERVICE);
//            ClipData data = ClipData.newPlainText(“result”, result.getContents());
//            manager.setPrimaryClip(data)
            Toast.makeText(context, result.contents, Toast.LENGTH_LONG).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}