package com.simats.endo_lifescan.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simats.endo_lifescan.network.RetrofitInstance
import com.simats.endo_lifescan.network.SignupRequest
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {

    var isLoading = mutableStateOf(false)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    fun signup(
        fullName: String,
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                val response = RetrofitInstance.api.signup(
                    SignupRequest(
                        fullName = fullName,
                        email = email,
                        password = password
                    )
                )

                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    errorMessage.value = "Signup failed. Please try again."
                }

            } catch (e: Exception) {
                errorMessage.value = "Network error"
            } finally {
                isLoading.value = false
            }
        }
    }
}