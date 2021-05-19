package com.capstone.personalmedicalrecord.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.databinding.FragmentHomeBinding
import java.util.*

class HomeFragment : Fragment() {
    private lateinit var preference: MyPreference
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        preference = MyPreference(requireActivity())

        var arr = preference.getEmail()?.split(" ") as ArrayList<String>
        var count = 0
        for (i in 0 until arr.size) {
            if (arr[i].length + count <= 10) {
                count += arr[i].length
                arr[i] = arr[i].capitalize(Locale.ROOT)
            } else {
                count += 2
                arr[i] = "${arr[i][0].toUpperCase()}."
            }
            count += 1
        }
        val text = arr.joinToString(" ")
        binding.greeting.text = resources.getString(R.string.greeting, text)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}