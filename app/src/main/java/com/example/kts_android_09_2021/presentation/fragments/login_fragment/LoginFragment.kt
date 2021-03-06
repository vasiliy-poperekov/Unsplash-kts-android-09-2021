package com.example.kts_android_09_2021.presentation.fragments.login_fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kts_android_09_2021.R
import com.example.kts_android_09_2021.databinding.FragmentLoginBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel: LoginFragmentViewModel by viewModel()
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
                    exception != null -> onAuthCodeFailed(exception)
                }
            }
        }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().finish()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
        bindViewModel()
    }

    private fun bindViewModel() {
        binding.bvLogin.setOnClickListener { viewModel.openLoginPage() }
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        everythingIsReadyObserver.collect { everythingIsReady ->
                            if (everythingIsReady)
                                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
                        }
                    }
                    launch { loadingObserver.collect(::updateIsLoading) }
                    launch { openAuthObserver.collect { if (it != null) openAuthPage(it) } }
                    launch { onAuthCodeFailedObserver.collect { if (it != null) onAuthCodeFailed(it) } }
                }
            }
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

    private fun onAuthCodeFailed(exception: AuthorizationException) {
        Toast.makeText(requireContext(), exception.toJsonString(), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        onBackPressedCallback.remove()
    }
}