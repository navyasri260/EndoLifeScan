package com.simats.endo_lifescan.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.simats.endo_lifescan.network.LoginRequest
import com.simats.endo_lifescan.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun EnterPasswordScreen(
    navController: NavController,
    email: String?,
    name: String? = null
) {

    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    fun isPasswordValid(pw: String): Boolean {
        return pw.length >= 8 &&
                pw.any { it.isUpperCase() } &&
                pw.any { it.isLowerCase() } &&
                pw.any { "!@#$%^&*()".contains(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Enter Password",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = email ?: "")

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (password.isNotBlank() && !isPasswordValid(password)) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Minimum 8 chars, include uppercase, lowercase & special character",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                if (email.isNullOrEmpty()) {
                    errorMessage = "Email missing"
                    return@Button
                }

                if (!isPasswordValid(password)) {
                    errorMessage = "Password does not meet requirements"
                    return@Button
                }

                isLoading = true
                errorMessage = null

                scope.launch {
                    try {
                        val response = withContext(Dispatchers.IO) {
                            RetrofitInstance.api.login(
                                LoginRequest(
                                    email = email,
                                    password = password
                                )
                            )
                        }

                        if (response.isSuccessful) {
                            val user = response.body()?.user

                            if (user != null) {
                                val safeName = URLEncoder.encode(
                                    user.full_name,
                                    StandardCharsets.UTF_8.toString()
                                )
                                val safeEmail = URLEncoder.encode(
                                    user.email,
                                    StandardCharsets.UTF_8.toString()
                                )

                                navController.navigate(
                                    "${Screen.Home.route}/$safeName/$safeEmail"
                                ) {
                                    popUpTo(Screen.WelcomeBack.route) { inclusive = true }
                                }
                            } else {
                                errorMessage = "Invalid login response"
                            }

                        } else {
                            errorMessage = "Invalid email or password"
                        }

                    } catch (e: Exception) {
                        errorMessage = "Network error"
                    } finally {
                        isLoading = false
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = password.isNotBlank()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Login")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = {
                navController.navigate(Screen.ForgotPassword.route)
            }
        ) {
            Text("Forgot Password?")
        }
    }
}