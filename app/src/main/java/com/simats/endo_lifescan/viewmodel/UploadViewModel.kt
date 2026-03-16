package com.simats.endo_lifescan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simats.endo_lifescan.network.RetrofitInstance
import com.simats.endo_lifescan.network.UploadResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadViewModel : ViewModel() {

    private val _uploadResult = MutableStateFlow<UploadResponse?>(null)
    val uploadResult: StateFlow<UploadResponse?> = _uploadResult

    fun uploadImages(
        image1: MultipartBody.Part,
        image2: MultipartBody.Part,
        image3: MultipartBody.Part,
        userId: RequestBody
    ) {

        viewModelScope.launch {

            try {

                val response = RetrofitInstance.api.uploadImages(
                    image1,
                    image2,
                    image3,
                    userId
                )

                if (response.isSuccessful) {
                    _uploadResult.value = response.body()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    // 🔹 Clear result after navigation
    fun clearResult() {
        _uploadResult.value = null
    }
}