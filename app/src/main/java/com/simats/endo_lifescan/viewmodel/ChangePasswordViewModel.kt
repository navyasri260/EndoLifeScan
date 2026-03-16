package com.simats.endo_lifescan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simats.endo_lifescan.network.RetrofitInstance
import kotlinx.coroutines.launch

class ChangePasswordViewModel : ViewModel() {

    fun changePassword(
        email: String,
        currentPassword: String,
        newPassword: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {

            try {

                val response = RetrofitInstance.api.changePassword(
                    mapOf(
                        "email" to email,
                        "current_password" to currentPassword,
                        "new_password" to newPassword
                    )
                )

                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Current password incorrect")
                }

            } catch (e: Exception) {
                onError("Network error")
            }

        }
    }
}