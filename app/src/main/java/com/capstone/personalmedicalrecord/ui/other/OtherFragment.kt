package com.capstone.personalmedicalrecord.ui.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.capstone.personalmedicalrecord.databinding.FragmentOtherBinding

class OtherFragment : Fragment() {

    private lateinit var otherViewModel: OtherViewModel
    private var _binding: FragmentOtherBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        otherViewModel =
            ViewModelProvider(this).get(OtherViewModel::class.java)

        _binding = FragmentOtherBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textOther
        otherViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}