package com.simats.endo_lifescan.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.io.OutputStreamWriter
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simats.endo_lifescan.viewmodel.ChangePasswordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController, name: String?, email: String?) {

    var newName by remember { mutableStateOf(name ?: "") }
    var newEmail by remember { mutableStateOf(email ?: "") }
    var isChangingPassword by remember { mutableStateOf(false) }
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf("") }

    val changePasswordViewModel: ChangePasswordViewModel = viewModel()

    val isFormChanged =
        newName != name ||
                newEmail != email ||
                (isChangingPassword &&
                        currentPassword.isNotBlank() &&
                        newPassword.isNotBlank() &&
                        confirmNewPassword.isNotBlank() &&
                        newPassword == confirmNewPassword)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .gradientBackground()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = newName,
                onValueChange = { newName = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = newEmail,
                onValueChange = { newEmail = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isChangingPassword) {

                OutlinedTextField(
                    value = currentPassword,
                    onValueChange = { currentPassword = it },
                    label = { Text("Current Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("New Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = confirmNewPassword,
                    onValueChange = { confirmNewPassword = it },
                    label = { Text("Confirm New Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                if (passwordError.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = passwordError,
                        color = Color.Red
                    )
                }

            } else {
                TextButton(onClick = { isChangingPassword = true }) {
                    Text("Change Password")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                enabled = isFormChanged,
                onClick = {

                    if (isChangingPassword) {

                        if (currentPassword.isBlank()) {
                            passwordError = "Enter current password"
                            return@Button
                        }

                        if (newPassword.isBlank()) {
                            passwordError = "Enter new password"
                            return@Button
                        }

                        if (newPassword != confirmNewPassword) {
                            passwordError = "Passwords do not match"
                            return@Button
                        }

                        passwordError = ""

                        // ✅ ViewModel API Call (Backend Integration)
                        changePasswordViewModel.changePassword(
                            email = email ?: "",
                            currentPassword = currentPassword,
                            newPassword = newPassword,

                            onSuccess = {
                                showSuccessDialog = true
                            },

                            onError = {
                                passwordError = it
                            }
                        )

                    } else {
                        showSuccessDialog = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF663399))
            ) {
                Text("Save Changes")
            }

            if (showSuccessDialog) {
                AlertDialog(
                    onDismissRequest = { },
                    confirmButton = {
                        TextButton(onClick = {
                            showSuccessDialog = false
                            navController.popBackStack()
                        }) {
                            Text("OK")
                        }
                    },
                    title = { Text("Success") },
                    text = { Text("Changes made successfully") }
                )
            }
        }
    }
}