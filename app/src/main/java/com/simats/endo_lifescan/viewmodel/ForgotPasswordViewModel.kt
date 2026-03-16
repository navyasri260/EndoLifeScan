package com.simats.endo_lifescan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simats.endo_lifescan.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForgotPasswordViewModel : ViewModel() {

    fun sendOtp(
        email: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.forgotPassword(
                        mapOf("email" to email)
                    )
                }

                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Email not registered")
                }

            } catch (e: Exception) {
                onError("Network error")
            }
        }
    }
}