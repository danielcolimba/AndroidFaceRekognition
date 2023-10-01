package com.dicolimba.testawsreko.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.dicolimba.testawsreko.databinding.ActivityMainBinding
import com.dicolimba.testawsreko.viewmodel.FaceViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val faceViewModel: FaceViewModel by viewModels()
    private var imageView: ImageView? = null
    private var byteArray: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageView = binding.imageView
        btnListeners()
    }

    private fun btnListeners() {
        binding.btnSelect.setOnClickListener {
            openGallery()
        }
        binding.btnAnalyze.setOnClickListener {
            if (byteArray != null) {
                faceViewModel.requestAWS(byteArray!!)
            }else{
                Toast.makeText(this, "Select an image first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openGallery() {
        // Crear un Intent para iniciar la actividad de selección de imágenes.
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.type = "image/*"
        requestImage.launch(i) // Iniciar la actividad de selección de imágenes.
    }

    private val requestImage = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {// La actividad devuelta proporcionó un resultado exitoso.
            val data: Intent? = result.data
            if (data != null) {
                // Procesar los datos de la actividad devuelta.
                val uri = data.data
                imageView?.setImageURI(uri)
                val inputStream = contentResolver.openInputStream(uri!!)
                byteArray = inputStream?.readBytes()
            }
        } else {
            Log.d(TAG, "onActivityResult: Failed")
        }
    }

    companion object{
        private const val TAG = "MainActivity"
    }
}