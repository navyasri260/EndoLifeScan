package com.simats.endo_lifescan.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import android.util.Patterns

@Composable
fun WelcomeBackScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    fun validateEmail(): Boolean {
        isError = !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        return !isError
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        // ✅ center vertically
        verticalArrangement = Arrangement.Center,

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Welcome back",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Enter your email to continue.")

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                isError = false
            },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            supportingText = {
                if (isError)
                    Text("Enter a valid email address")
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (validateEmail()) {
                    val name = email
                        .substringBefore("@")
                        .replace(".", " ")
                        .replaceFirstChar { it.uppercase() }

                    navController.navigate(
                        "${Screen.EnterPassword.route}/$email/$name"
                    )
                }
            },
            enabled = email.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}