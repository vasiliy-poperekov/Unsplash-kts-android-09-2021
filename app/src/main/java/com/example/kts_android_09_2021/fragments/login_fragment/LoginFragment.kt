package com.example.kts_android_09_2021.fragments.login_fragment

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kts_android_09_2021.R
import com.example.kts_android_09_2021.databinding.FragmentLoginBinding
import timber.log.Timber

class LoginFragment : Fragment() {

    var binding: FragmentLoginBinding? = null
    var emailFlag = false
    var passwordFlag = false
    lateinit var viewModel: SavedStateLoginViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.d("LoginFragment onAttach completed")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("LoginFragment onCreate completed")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        Timber.d("LoginFragment onCreateView completed")
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.d("LoginFragment onViewCreated completed")

        viewModel = ViewModelProvider(
            this,
            SavedStateViewModelFactory(requireActivity().application, this)
        ).get(SavedStateLoginViewModel::class.java)

        setButtonClickListener()

        binding?.etEmail?.addTextChangedListener {
            val emailString = binding?.etEmail?.text?.toString()!!
            emailFlag = checkEmail(emailString)
            if (emailFlag) binding?.tilEmail?.isErrorEnabled = false
            setButtonClickListener()
        }

        binding?.etPassword?.addTextChangedListener {
            val passwordString = binding?.etPassword?.text?.toString()!!
            passwordFlag = passwordString.length >= 8
            if (passwordFlag) binding?.tilPassword?.isErrorEnabled = false
            setButtonClickListener()
        }
    }

    fun setButtonClickListener() {
        if (emailFlag && passwordFlag) {
            binding?.bvLogin?.setBackgroundResource(R.color.black)
            binding?.bvLogin?.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding?.bvLogin?.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
            }
        } else {
            binding?.bvLogin?.setBackgroundResource(R.color.grey_button_color)
            binding?.bvLogin?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            binding?.bvLogin?.setOnClickListener {
                if (!emailFlag) {
                    binding?.tilEmail?.error = getString(R.string.enter_correct_email)
                }
                if (!passwordFlag) {
                    binding?.tilPassword?.error = getString(R.string.need_more_than_8_symbols)
                }
            }
        }
    }

    fun checkEmail(emailString: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(emailString).matches()

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Timber.d("LoginFragment onViewStateRestored completed")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("LoginFragment onStart completed")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("LoginFragment onResume completed")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("LoginFragment onPause completed")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("LoginFragment onStop completed")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d("LoginFragment onSaveInstanceState completed")

        if (::viewModel.isInitialized) {
            viewModel.saveState()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        Timber.d("LoginFragment onDestroyView completed")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("LoginFragment onDestroy completed")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("LoginFragment onDetach completed")
    }
}