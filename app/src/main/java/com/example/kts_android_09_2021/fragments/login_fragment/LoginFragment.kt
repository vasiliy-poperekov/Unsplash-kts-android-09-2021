package com.example.kts_android_09_2021.fragments.login_fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kts_android_09_2021.R
import com.example.kts_android_09_2021.databinding.FragmentLoginBinding
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel: LoginFragmentViewModel by viewModels()
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                val tokenRequest = AuthorizationResponse.fromIntent(activityResult.data!!)
                    ?.createTokenExchangeRequest()
                val exception = AuthorizationException.fromIntent(activityResult.data!!)

                when {
                    tokenRequest != null && exception == null -> viewModel.onAuthCodeReceived(
                        tokenRequest
                    )
                    exception != null -> viewModel.onAuthCodeFailed(exception)
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        bindViewModel()
    }

    private fun bindViewModel() {
        binding.bvLogin.setOnClickListener { viewModel.openLoginPage() }
        with(viewModel) {
            loadingLiveData.observe(viewLifecycleOwner, ::updateIsLoading)
            openAuthLiveData.observe(viewLifecycleOwner, ::openAuthPage)
            authSuccessLiveData.observe(viewLifecycleOwner, {
                if (it) findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
            })
        }
    }

    private fun updateIsLoading(isLoading: Boolean) {
        with(binding) {
            bvLogin.isVisible = !isLoading
            pbLogin.isVisible = isLoading
        }
    }

    private fun openAuthPage(intent: Intent) {
        resultLauncher.launch(intent)
    }
}