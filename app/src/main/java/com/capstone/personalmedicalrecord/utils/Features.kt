package com.capstone.personalmedicalrecord.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import java.io.File

object Features {
    private var photoFile: File? = null
    private const val FILE_NAME = "photo.jpg"

    fun FragmentActivity.takePhoto(): String {
        var uri = ""
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoFile = getPhotoFile(applicationContext)
        val providerFile = FileProvider.getUriForFile(
            applicationContext,
            "com.capstone.personalmedicalrecord.fileprovider",
            photoFile as File
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)

        if (intent.resolveActivity(applicationContext.packageManager) != null) {
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val takenImage = BitmapFactory.decodeFile(photoFile?.absolutePath)
//            binding.imageView.setImageBitmap(takenImage)
                    uri = photoFile?.absolutePath.toString()
                    Toast.makeText(applicationContext, photoFile?.absolutePath, Toast.LENGTH_SHORT)
                        .show()
                }
            }.launch(intent)
        } else {
            Toast.makeText(applicationContext, "Camera could not open", Toast.LENGTH_SHORT).show()
        }
        return uri
    }

    private fun getPhotoFile(context: Context): File {
        val storageDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(FILE_NAME, ".jpg", storageDirectory)
    }
}