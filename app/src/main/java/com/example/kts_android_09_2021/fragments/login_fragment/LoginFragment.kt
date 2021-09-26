package com.example.kts_android_09_2021.fragments.login_fragment

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kts_android_09_2021.R
import com.example.kts_android_09_2021.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)
    private var emailIsCorrect = false
    private var passwordIsCorrect = false
    private val viewModel: LoginFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.etEmail.setText(viewModel.emailStringLiveData)
        binding.etPassword.setText(viewModel.passwordStringLiveData)

        setButtonClickListener()

        binding.etEmail.addTextChangedListener {
            val emailString = binding.etEmail.text?.toString()!!
            viewModel.changeEmail(emailString)
        }

        binding.etPassword.addTextChangedListener {
            val passwordString = binding.etPassword.text?.toString()!!
            viewModel.changePassword(passwordString)
        }

        viewModel.emailIsCorrectLiveData.observe(viewLifecycleOwner, {
            emailIsCorrect = it
            setButtonClickListener()
            if (emailIsCorrect) binding.tilEmail.isErrorEnabled = false
        })
        viewModel.passwordIsCorrectLiveData.observe(viewLifecycleOwner, {
            passwordIsCorrect = it
            setButtonClickListener()
            if (passwordIsCorrect) binding.tilPassword.isErrorEnabled = false
        })
    }

    private fun setButtonClickListener() {
        if (emailIsCorrect && passwordIsCorrect) {
            binding.bvLogin.setBackgroundResource(R.color.black)
            binding.bvLogin.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding.bvLogin.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
            }
        } else {
            binding.bvLogin.setBackgroundResource(R.color.grey_button_color)
            binding.bvLogin.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            binding.bvLogin.setOnClickListener {
                if (!emailIsCorrect) {
                    binding.tilEmail.error = getString(R.string.login_enter_correct_email)
                }
                if (!passwordIsCorrect) {
                    binding.tilPassword.error = getString(R.string.login_need_more_than_8_symbols)
                }
            }
        }
    }
}