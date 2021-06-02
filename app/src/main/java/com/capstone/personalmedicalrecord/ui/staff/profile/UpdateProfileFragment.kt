package com.capstone.personalmedicalrecord.ui.staff.profile

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
import com.bumptech.glide.Glide
import com.capstone.personalmedicalrecord.MyPreference
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.core.domain.model.Staff
import com.capstone.personalmedicalrecord.databinding.FragmentStaffUpdateProfileBinding
import com.capstone.personalmedicalrecord.utils.Utility.clickBack
import com.capstone.personalmedicalrecord.utils.Utility.hideKeyboard
import com.capstone.personalmedicalrecord.utils.Utility.setImage
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class UpdateProfileFragment : Fragment() {

    private lateinit var preference: MyPreference
    private var _binding: FragmentStaffUpdateProfileBinding? = null
    private val binding get() = _binding as FragmentStaffUpdateProfileBinding
    private val viewModel: UpdateStaffViewModel by viewModel()

    private var storageReference: StorageReference? = null
    private var filePath: Uri? = null

    private var currentPhotoPath = ""
    private var oldPicture = ""
    private var passwd = ""
    private var access = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentStaffUpdateProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preference = MyPreference(requireContext())

        storageReference = FirebaseStorage.getInstance().reference

        viewModel.getPatient(preference.getId()).observe(viewLifecycleOwner, { staff ->
            if (staff.data != null) {
                with(staff.data) {
                    binding.inputFullName.setText(name)
                    binding.inputEmail.setText(email)
                    binding.inputPhoneNumber.setText(phoneNumber)
                    binding.inputHospital.setText(hospital)
                    passwd = password
                    oldPicture = picture

                    binding.avatar.setImage(picture)
                }
            }
        })


        binding.saveChangesBtn.setOnClickListener {
            val staff = Staff(
                id = preference.getId(),
                name = binding.inputFullName.text.toString(),
                email = binding.inputEmail.text.toString(),
                password = passwd,
                phoneNumber = binding.inputPhoneNumber.text.toString(),
                hospital = binding.inputHospital.text.toString(),
                picture = oldPicture
            )
            viewModel.update(staff)
            it.hideKeyboard()
            activity?.supportFragmentManager?.popBackStack()
        }

        binding.changePhoto.setOnClickListener {
            val singleItems = arrayOf("Take a Photo", "Choose a photo")
            var checkedItem = 0

            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.add_record_text))
                .setNeutralButton(getString(R.string.cancel), null)
                .setPositiveButton(getString(R.string.ok)) { _, _ ->
                    when (checkedItem) {
                        0 -> requestPhoto()
                        1 -> choosePhoto()
                    }
                }
                .setSingleChoiceItems(singleItems, 0) { _, which ->
                    checkedItem = which
                }
                .show()
        }

        activity?.clickBack(binding.backBtn)
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
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        choosePhoto.launch(Intent.createChooser(intent, "Select Picture"))
    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                if (access == "camera") {
                    takePhoto()
                } else {
                    chooseImageGallery()
                }
            } else {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    private val takePhoto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                Glide.with(requireContext())
                    .load(File(currentPhotoPath))
                    .centerCrop()
                    .into(binding.avatar)
                uploadImage(oldPicture, Uri.fromFile(File(currentPhotoPath)))
            }
        }

    private val choosePhoto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                filePath = result.data?.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        requireContext().contentResolver,
                        filePath
                    )
                    Glide.with(requireContext())
                        .asBitmap()
                        .load(bitmap)
                        .centerCrop()
                        .into(binding.avatar)

                    uploadImage(oldPicture, filePath as Uri)

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    private fun uploadImage(picture: String, filePath: Uri) {
            if (picture.length > 2) {
                val file = FirebaseStorage.getInstance().getReferenceFromUrl(picture)
                file.delete()
                    .addOnSuccessListener {
                        Log.d("deletePhoto", "Delete Photo from Firebase Storage")
                    }
                    .addOnFailureListener {
                        Log.e("deletePhoto", "Error delete photo")
                    }
            }
            if (filePath != null) {
                val ref = storageReference?.child("profile/" + UUID.randomUUID().toString())
                val uploadTask = ref?.putFile(filePath)

                uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let { throw it }
                    }
                    return@Continuation ref.downloadUrl
                })?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        viewModel.updatePicture(preference.getId(), downloadUri.toString())
                    }
                }?.addOnFailureListener {
                    Log.e("error on upload image", it.message.toString())
                }
            } else {
                Toast.makeText(context, "Please Upload an Image", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}