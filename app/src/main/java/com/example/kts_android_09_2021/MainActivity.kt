package com.example.kts_android_09_2021

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kts_android_09_2021.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.d("MainActivity onCreate completed")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("MainActivity onStart completed")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("MainActivity onResume completed")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("MainActivity onPause completed")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("MainActivity onStop completed")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.d("MainActivity onRestart completed")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("MainActivity onDestroy completed")
    }
}