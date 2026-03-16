package com.simats.endo_lifescan.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simats.endo_lifescan.viewmodel.SignupViewModel

@Composable
fun CreateAccountScreen(
    navController: NavController,
    signupViewModel: SignupViewModel = viewModel()
) {

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var fullNameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    val passwordValid =
        password.length >= 8 &&
                password.any { it.isUpperCase() } &&
                password.any { it.isLowerCase() } &&
                password.any { "!@#$%^&*()".contains(it) }
    val isLoading by signupViewModel.isLoading
    val errorMessage by signupViewModel.errorMessage

    fun validate(): Boolean {
        fullNameError =
            if (fullName.all { it.isLetter() || it.isWhitespace() }) null
            else "Letters and spaces only."

        emailError =
            if (email.endsWith("@gmail.com")) null
            else "Must be a gmail.com address."

        passwordError = when {
            password.length < 8 -> "Minimum 8 characters required."
            password.none { it.isUpperCase() } -> "Must contain uppercase letter."
            password.none { it.isLowerCase() } -> "Must contain lowercase letter."
            password.none { "!@#$%^&*()".contains(it) } -> "Must contain special character."
            else -> null
        }

        confirmPasswordError =
            if (password == confirmPassword) null
            else "Passwords do not match."

        return fullNameError == null &&
                emailError == null &&
                passwordError == null &&
                confirmPasswordError == null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Create Account", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Sign up to start analyzing files.")
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it; fullNameError = null },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth(),
            isError = fullNameError != null,
            supportingText = { fullNameError?.let { Text(it) } }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it; emailError = null },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError != null,
            supportingText = { emailError?.let { Text(it) } }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it; passwordError = null },
            label = { Text("Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = passwordError != null,
            supportingText = { passwordError?.let { Text(it) } }
        )

        if (password.isNotEmpty()) {
            Text(
                text = "Minimum 8 characters, include uppercase, lowercase & special character",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
//        }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it; confirmPasswordError = null },
                label = { Text("Confirm Password") },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                isError = confirmPasswordError != null,
                supportingText = { confirmPasswordError?.let { Text(it) } }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (!validate()) return@Button

                    signupViewModel.signup(
                        fullName = fullName,
                        email = email,
                        password = password
                    ) {
                        navController.navigate(Screen.AccountCreated.route)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Create Account")
                }
            }

            errorMessage?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}