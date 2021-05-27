package com.capstone.personalmedicalrecord.ui.patient.data.records

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.core.domain.model.Record
import com.capstone.personalmedicalrecord.databinding.FragmentRecordsBinding
import com.capstone.personalmedicalrecord.ui.patient.data.DataViewModel
import com.capstone.personalmedicalrecord.ui.patient.data.DetailDataFragment
import com.capstone.personalmedicalrecord.utils.DataDummy
import com.capstone.personalmedicalrecord.utils.Utility.navigateTo
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File

class RecordsFragment : Fragment(), RecordsCallback {

    private lateinit var preference: MyPreference
    private lateinit var viewModel: DataViewModel
    private lateinit var recordsAdapter: RecordsAdapter
    private var _binding: FragmentRecordsBinding? = null
    private val binding get() = _binding as FragmentRecordsBinding

    private var photoFile: File? = null
    private var fileUri: Uri? = null
    private var filePath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preference = MyPreference(requireActivity())
        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)

        if (activity != null) {
            recordsAdapter = RecordsAdapter(this)
            val filter = DataDummy.listRecords.filter { note -> note.idPatient == preference.getId() }
            recordsAdapter.setData(filter)
            with(binding.rvRecords) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = recordsAdapter
            }

            binding.plusBtn.setOnClickListener {
                val singleItems = arrayOf("Take a Photo", "Choose a photo", "Choose a document")
                var checkedItem = 0

                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(getString(R.string.add_record_text))
                    .setNeutralButton(getString(R.string.cancel), null)
                    .setPositiveButton(getString(R.string.ok)) { _, _ ->
                        when (checkedItem) {
                            0 -> takePhoto()
                            1 -> choosePhoto()
                            2 -> chooseDocument()
                        }
                    }
                    .setSingleChoiceItems(singleItems, 0) { _, which ->
                        checkedItem = which
                    }
                    .show()
            }
        }
    }

    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoFile = getPhotoFile()
        val providerFile = FileProvider.getUriForFile(
            requireContext(),
            "com.capstone.personalmedicalrecord.fileprovider",
            photoFile as File
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)

        if (intent.resolveActivity(requireContext().packageManager) != null) {
            takePhoto.launch(intent)
        } else {
            Toast.makeText(context, "Camera could not open", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getPhotoFile(): File {
        val storageDirectory = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(FILE_NAME, ".jpg", storageDirectory)
    }

    private fun choosePhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requireContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                chooseImageGallery()
            }
        } else {
            chooseImageGallery()
        }
    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        choosePhoto.launch(intent)
    }

    private fun chooseDocument() {
        var intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent = Intent.createChooser(intent, "Choose a file")
        chooseDocument.launch(intent)
    }

    private val takePhoto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val takenImage = BitmapFactory.decodeFile(photoFile?.absolutePath)
//            binding.imageView.setImageBitmap(takenImage)
                Toast.makeText(context, photoFile?.absolutePath, Toast.LENGTH_SHORT).show()
            }
        }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                chooseImageGallery()
            } else {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    private val choosePhoto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
//            viewImage.setImageURI(data?.data)
                Toast.makeText(context, "Image Choose", Toast.LENGTH_SHORT).show()
            }
        }

    private val chooseDocument =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                fileUri = result.data?.data
                filePath = fileUri?.path
                Toast.makeText(context, filePath, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onItemClick(record: Record) {
        viewModel.setType("records")
        activity?.navigateTo(DetailDataFragment(), R.id.frame)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FILE_NAME = "photo.jpg"
    }
}