package com.simats.endo_lifescan.network

import com.google.gson.annotations.SerializedName

data class SignupRequest(

    @SerializedName("full_name")
    val fullName: String,

    val email: String,
    val password: String
)