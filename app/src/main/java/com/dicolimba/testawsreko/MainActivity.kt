package com.dicolimba.testawsreko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicolimba.testawsreko.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btnListeners()
    }

    private fun btnListeners() {
        binding.btnSelect.setOnClickListener {

        }
        binding.btnAnalyze.setOnClickListener {

        }
    }
}