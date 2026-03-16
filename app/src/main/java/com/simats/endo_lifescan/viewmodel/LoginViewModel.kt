package com.simats.endo_lifescan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simats.endo_lifescan.network.LoginRequest
import com.simats.endo_lifescan.network.RetrofitInstance
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.login(
                    LoginRequest(
                        email = email,
                        password = password
                    )
                )

                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Invalid credentials")
                }

            } catch (e: Exception) {
                onError("Network error")
            }
        }
    }
}