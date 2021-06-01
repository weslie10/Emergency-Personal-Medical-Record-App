package com.capstone.personalmedicalrecord.ui.patient.data.records

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.core.domain.model.Record
import com.capstone.personalmedicalrecord.databinding.FragmentRecordsBinding
import com.capstone.personalmedicalrecord.ui.patient.data.DetailDataFragment
import com.capstone.personalmedicalrecord.utils.DataDummy
import com.capstone.personalmedicalrecord.utils.Utility.navigateTo
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class RecordsFragment : Fragment(), RecordsCallback {

    private lateinit var preference: MyPreference

    private lateinit var recordsAdapter: RecordsAdapter
    private var _binding: FragmentRecordsBinding? = null
    private val binding get() = _binding as FragmentRecordsBinding
    private val viewModel: RecordsViewModel by viewModel()

    private lateinit var currentPhotoPath: String
    private var fileUri: Uri? = null
    private var filePath: String? = null
    private var access = ""

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

        if (activity != null) {
            recordsAdapter = RecordsAdapter(this)
            val id = preference.getId()
            viewModel.getRecords(id).observe(viewLifecycleOwner, { records ->
                if (records != null) {
                    recordsAdapter.setData(records)
                }
            })
            with(binding.rvRecords) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = recordsAdapter
            }

            binding.plusBtn.setOnClickListener {
                viewModel.getPatient(preference.getId()).observe(viewLifecycleOwner, {
                    if (it.term) {
                        val singleItems = arrayOf("Take a Photo", "Choose a photo", "Choose a document", "Manual Input")
                        var checkedItem = 0

                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle(getString(R.string.add_record_text))
                            .setNeutralButton(getString(R.string.cancel), null)
                            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                                when (checkedItem) {
                                    0 -> requestPhoto()
                                    1 -> choosePhoto()
                                    2 -> chooseDocument()
                                    3 -> manualInput()
                                }
                            }
                            .setSingleChoiceItems(singleItems, 0) { _, which ->
                                checkedItem = which
                            }
                            .show()
                    } else {
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Informed Consent")
                            .setMessage("""
                                I hereby agree to share my personal medical record to this application.
                            """.trimIndent())
                            .setPositiveButton("I agree") { _, _ ->
                                val patient = it
                                it.term = true
                                viewModel.update(patient)
                            }
                            .setNegativeButton("I refuse") { _, _ ->

                            }
                            .show()
                    }
                })
            }
        }
    }

    private fun manualInput() {
        val fragment = AddOrUpdateRecordFragment()
        val bundle = Bundle().apply {
            putString("state", "Add")
        }
        fragment.arguments = bundle
        activity?.navigateTo(fragment, R.id.frame)
    }

    private fun requestPhoto() {
        if (requireContext().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            access = "camera"
            requestPermission.launch(Manifest.permission.CAMERA)
        } else {
            takePhoto()
        }
    }

    private fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    Toast.makeText(context, "Camera could not open", Toast.LENGTH_SHORT).show()
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.capstone.personalmedicalrecord.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    takePhoto.launch(takePictureIntent)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File =
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) as File
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun choosePhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requireContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                access = "storage"
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
                Log.d("data", result.data?.extras?.get("data").toString())
//                val takenImage = BitmapFactory.decodeFile(photoFile?.absolutePath)
//            binding.imageView.setImageBitmap(takenImage)
                Toast.makeText(context, currentPhotoPath, Toast.LENGTH_SHORT).show()
            }
        }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                if (access == "camera") {
                    takePhoto()
                } else
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
        val fragment = DetailDataFragment()
        val bundle = Bundle().apply {
            putInt("id", record.id)
            putString("type", "records")
        }
        fragment.arguments = bundle
        activity?.navigateTo(fragment, R.id.frame)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}