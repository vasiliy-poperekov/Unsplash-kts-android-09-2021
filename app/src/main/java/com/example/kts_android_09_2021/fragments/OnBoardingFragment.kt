package com.example.kts_android_09_2021.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kts_android_09_2021.databinding.FragmentOnBoardingBinding
import timber.log.Timber

class OnBoardingFragment : Fragment() {
    var binding: FragmentOnBoardingBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.d("OnBoardingFragment onAttach completed")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("OnBoardingFragment onCreate completed")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        Timber.d("OnBoardingFragment onCreateView completed")
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.d("OnBoardingFragment onViewCreated completed")
        binding?.bvWelcomeNext?.setOnClickListener {
            findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment())
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Timber.d("OnBoardingFragment onViewStateRestored completed")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("OnBoardingFragment onStart completed")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("OnBoardingFragment onResume completed")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("OnBoardingFragment onPause completed")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("OnBoardingFragment onStop completed")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d("OnBoardingFragment onSaveInstanceState completed")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        Timber.d("OnBoardingFragment onDestroyView completed")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("OnBoardingFragment onDestroy completed")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("OnBoardingFragment onDetach completed")
    }

}