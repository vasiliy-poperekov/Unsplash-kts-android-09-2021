package com.example.kts_android_09_2021.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kts_android_09_2021.databinding.FragmentMainBinding
import timber.log.Timber

class MainFragment : Fragment() {
    var binding: FragmentMainBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.d("MainFragment onAttach completed")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("MainFragment onCreate completed")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        Timber.d("MainFragment onCreateView completed")
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("MainFragment onViewCreated completed")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Timber.d("MainFragment onViewStateRestored completed")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("MainFragment onStart completed")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("MainFragment onResume completed")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("MainFragment onPause completed")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("MainFragment onStop completed")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d("MainFragment onSaveInstanceState completed")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        Timber.d("MainFragment onDestroyView completed")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("MainFragment onDestroy completed")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("MainFragment onDetach completed")
    }
}