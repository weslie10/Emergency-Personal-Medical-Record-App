package com.capstone.personalmedicalrecord.ui.patient.profile

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.fragment.app.Fragment
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.databinding.FragmentCheckIdBinding
import com.capstone.personalmedicalrecord.utils.Utility.clickBack
import com.google.zxing.WriterException

class CheckIdFragment : Fragment() {
    private lateinit var preference: MyPreference
    private var _binding: FragmentCheckIdBinding? = null
    private val binding get() = _binding as FragmentCheckIdBinding

    var bitmap: Bitmap? = null
    var qrgEncoder: QRGEncoder? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCheckIdBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preference = MyPreference(requireActivity())

        activity?.clickBack(binding.backBtn)

        binding.link.text = preference.getId()

//        paintFlags =
//            paintFlags or Paint.UNDERLINE_TEXT_FLAG
//
//        setOnClickListener {
//            val clipboardManager =
//                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//            val clipData = ClipData.newPlainText("text", text)
//            clipboardManager.setPrimaryClip(clipData)
//            Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_LONG).show()
//        }

        qrgEncoder = QRGEncoder(binding.link.text.toString(), null, QRGContents.Type.TEXT, 300)
        try {
            bitmap = qrgEncoder!!.encodeAsBitmap()
            binding.qrCode.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            Log.e("Tag", e.toString())
        }
    }
}