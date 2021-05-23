package com.capstone.personalmedicalrecord.ui.staff.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.databinding.FragmentStaffHomeBinding
import com.capstone.personalmedicalrecord.utils.Utility.dateNow
import com.capstone.personalmedicalrecord.utils.Utility.simpleText

class HomeFragment : Fragment() {
    private lateinit var preference: MyPreference
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentStaffHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentStaffHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        preference = MyPreference(requireActivity())

        val arr = preference.getEmail()?.split(" ")?.toMutableList() as MutableList<String>
        val text = arr.simpleText()
        binding.greeting.text = resources.getString(R.string.greeting, text)
        binding.date.dateNow()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}