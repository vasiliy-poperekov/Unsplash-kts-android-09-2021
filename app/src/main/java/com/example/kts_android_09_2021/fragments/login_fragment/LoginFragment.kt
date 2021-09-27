package com.example.kts_android_09_2021.fragments.login_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kts_android_09_2021.R
import com.example.kts_android_09_2021.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private val viewModel: LoginFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.etEmail?.setText(viewModel.emailString)
        binding?.etPassword?.setText(viewModel.passwordString)

        setButtonClickListener()

        binding?.etEmail?.addTextChangedListener {
            val emailString = binding?.etEmail?.text?.toString()!!
            viewModel.changeEmail(emailString)
        }

        binding?.etPassword?.addTextChangedListener {
            val passwordString = binding?.etPassword?.text?.toString()!!
            viewModel.changePassword(passwordString)
        }

        viewModel.stateFieldsLiveData.observe(viewLifecycleOwner, {
            setButtonClickListener()
            if (viewModel.stateFieldsLiveData.value?.emailIsCorrect!!) binding?.tilEmail?.isErrorEnabled =
                false
            if (viewModel.stateFieldsLiveData.value?.passwordIsCorrect!!) binding?.tilPassword?.isErrorEnabled =
                false
        })
    }

    private fun setButtonClickListener() {
        if (viewModel.stateFieldsLiveData.value?.emailIsCorrect!! && viewModel.stateFieldsLiveData.value?.passwordIsCorrect!!) {
            with(binding?.bvLogin!!) {
                setBackgroundResource(R.color.black)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                setOnClickListener {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
                }
            }
        } else {
            with(binding?.bvLogin!!) {
                setBackgroundResource(R.color.grey_button_color)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                setOnClickListener {
                    if (!viewModel.stateFieldsLiveData.value?.emailIsCorrect!!) {
                        binding?.tilEmail?.error = getString(R.string.login_enter_correct_email)
                    }
                    if (!viewModel.stateFieldsLiveData.value?.passwordIsCorrect!!) {
                        binding?.tilPassword?.error =
                            getString(R.string.login_need_more_than_8_symbols)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}