package com.example.retrofittask_2021

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import coil.load
import com.example.retrofittask_2021.databinding.CatDetailFragmentBinding
import com.example.retrofittask_2021.network.CatPhoto
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class CatDetailFragment(private val catPhoto: CatPhoto) : Fragment() {

    private var _binding: CatDetailFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)
    private var listener: CatListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = activity as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CatDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detailIv.load(catPhoto.imgSrcUrl)

        binding.buttonBackIv.setOnClickListener {
            listener?.openListFragment()
        }

        binding.buttonSaveIv.setOnClickListener {
            saveImageToStorage()
        }
    }

    private fun saveImageToStorage() {
        val bitmap: Bitmap = binding.detailIv.drawable.toBitmap()
        val filename = "${System.currentTimeMillis()}.jpg"

        var fos: OutputStream? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context?.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(context, "image saved", Toast.LENGTH_SHORT).show()
        }
    }
}
