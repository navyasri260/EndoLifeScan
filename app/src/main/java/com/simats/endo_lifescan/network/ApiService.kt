package com.simats.endo_lifescan.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface ApiService {

    // 🔐 SIGN UP
    @POST("signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): Response<SignupResponse>

    // 🔐 LOGIN
    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    // 🔑 FORGOT PASSWORD
    @POST("forgot-password")
    suspend fun forgotPassword(
        @Body body: Map<String, String>
    ): Response<Unit>

    // 🔁 RESET PASSWORD
    @POST("reset-password")
    suspend fun resetPassword(
        @Body body: Map<String, String>
    ): Response<Unit>

    // 🔒 CHANGE PASSWORD
    @PUT("change-password")
    suspend fun changePassword(
        @Body body: Map<String, String>
    ): Response<Unit>

    // 📤 IMAGE UPLOAD
    @Multipart
    @POST("upload")
    suspend fun uploadImages(
        @Part image1: MultipartBody.Part,
        @Part image2: MultipartBody.Part,
        @Part image3: MultipartBody.Part,
        @Part("user_id") userId: RequestBody
    ): Response<UploadResponse>

}