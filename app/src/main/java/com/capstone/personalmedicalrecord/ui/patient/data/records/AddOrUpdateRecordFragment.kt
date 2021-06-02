package com.capstone.personalmedicalrecord.ui.patient.data.records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.core.domain.model.Record
import com.capstone.personalmedicalrecord.databinding.FragmentAddOrUpdateRecordBinding
import com.capstone.personalmedicalrecord.utils.Utility.clickBack
import com.capstone.personalmedicalrecord.utils.Utility.getDate
import com.capstone.personalmedicalrecord.utils.Utility.hideKeyboard
import org.koin.android.viewmodel.ext.android.viewModel

class AddOrUpdateRecordFragment : Fragment() {

    private var _binding: FragmentAddOrUpdateRecordBinding? = null
    private val binding get() = _binding as FragmentAddOrUpdateRecordBinding

    private val viewModel: RecordAddUpdateViewModel by viewModel()
    private lateinit var preference: MyPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddOrUpdateRecordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preference = MyPreference(requireContext())

        val arg = this.arguments
        if (arg != null) {
            when (arg.getString("state")) {
                "Add" -> {
                    addRecord()
                }
                "Update" -> {
                    val id = arg.getString("id").toString()
                    viewModel.getRecord(id).observe(viewLifecycleOwner, { record ->
                        binding.inputHaematocrit.setText(record.haematocrit.toString())
                        binding.inputHaemoglobin.setText(record.haemoglobin.toString())
                        binding.inputErythrocyte.setText(record.erythrocyte.toString())
                        binding.inputLeucocyte.setText(record.leucocyte.toString())
                        binding.inputThrombocyte.setText(record.thrombocyte.toString())
                        binding.inputMch.setText(record.mch.toString())
                        binding.inputMchc.setText(record.mchc.toString())
                        binding.inputMcv.setText(record.mcv.toString())
                    })
                    updateRecord(id)
                }
            }
        }
        activity?.clickBack(binding.backBtn)
    }

    private fun addRecord() {
        binding.recordBtn.apply {
            text = resources.getString(R.string.add_record)
            setOnClickListener {
                viewModel.insert(
                    Record(
                        date = getDate(),
                        haematocrit = binding.inputHaematocrit.text.toString().toDouble(),
                        haemoglobin = binding.inputHaemoglobin.text.toString().toDouble(),
                        erythrocyte = binding.inputErythrocyte.text.toString().toDouble(),
                        leucocyte = binding.inputLeucocyte.text.toString().toDouble(),
                        thrombocyte = binding.inputThrombocyte.text.toString().toInt(),
                        mch = binding.inputMch.text.toString().toDouble(),
                        mchc = binding.inputMchc.text.toString().toDouble(),
                        mcv = binding.inputMcv.text.toString().toDouble(),
                        idPatient = preference.getId()
                    )
                )
                it.hideKeyboard()
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }

    private fun updateRecord(id: String) {
        binding.recordBtn.apply {
            text = resources.getString(R.string.update_record)
            setOnClickListener {
                viewModel.update(
                    Record(
                        id = id,
                        date = getDate(),
                        haematocrit = binding.inputHaematocrit.text.toString().toDouble(),
                        haemoglobin = binding.inputHaemoglobin.text.toString().toDouble(),
                        erythrocyte = binding.inputErythrocyte.text.toString().toDouble(),
                        leucocyte = binding.inputLeucocyte.text.toString().toDouble(),
                        thrombocyte = binding.inputThrombocyte.text.toString().toInt(),
                        mch = binding.inputMch.text.toString().toDouble(),
                        mchc = binding.inputMchc.text.toString().toDouble(),
                        mcv = binding.inputMcv.text.toString().toDouble(),
                        idPatient = preference.getId()
                    )
                )
                it.hideKeyboard()
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }
}