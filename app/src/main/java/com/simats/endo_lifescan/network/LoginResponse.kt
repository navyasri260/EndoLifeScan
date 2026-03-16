package com.simats.endo_lifescan.network

data class LoginResponse(
    val status: String,
    val user: User?
)

data class User(
    val id: Int,
    val full_name: String,
    val email: String
)