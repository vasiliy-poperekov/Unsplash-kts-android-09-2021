package com.example.kts_android_09_2021

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kts_android_09_2021.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}