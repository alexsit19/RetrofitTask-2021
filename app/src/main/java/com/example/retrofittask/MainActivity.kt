package com.example.retrofittask

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofittask_2021.R
import com.example.retrofittask_2021.databinding.ActivityMainBinding
import com.example.retrofittask.network.CatPhoto
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class MainActivity : AppCompatActivity(), CatListener {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        supportFragmentManager.beginTransaction()
            .add(R.id.place_for_fragment, CatListFragment(), "catlistfragment")
            .commit()
    }

    override fun openDetailFragment(catPhoto: CatPhoto) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.card_flip_right_in,
                R.anim.card_flip_right_out,
                R.anim.card_flip_left_in,
                R.anim.card_flip_left_out
            )
            .replace(R.id.place_for_fragment, CatDetailFragment(catPhoto))
            .addToBackStack(null)
            .commit()
    }

    override fun openListFragment() {
        supportFragmentManager.popBackStack()
    }

    fun saveImageToStorage(bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"

        var fos: OutputStream? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.contentResolver?.also { resolver ->
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
            bitmap.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY, it)
            Toast.makeText(this, "image saved", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        openListFragment()
    }

    companion object {
        private const val IMAGE_QUALITY = 100
    }
}
