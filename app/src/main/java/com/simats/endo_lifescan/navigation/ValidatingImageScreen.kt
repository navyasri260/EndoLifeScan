package com.simats.endo_lifescan.navigation

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.simats.endo_lifescan.network.RetrofitInstance
import com.simats.endo_lifescan.utils.uriToFile
import com.simats.endo_lifescan.utils.fileToMultipart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull

@Composable
fun ValidatingImageScreen(
    navController: NavController,
    name: String?,
    email: String?,
    imageUri1: String?,
    imageUri2: String?,
    imageUri3: String?
) {

    val context = LocalContext.current

    var isLoading by remember { mutableStateOf(true) }
    var resultMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {

        try {

            val file1 = uriToFile(context, Uri.parse(imageUri1 ?: ""))
            val file2 = uriToFile(context, Uri.parse(imageUri2 ?: ""))
            val file3 = uriToFile(context, Uri.parse(imageUri3 ?: ""))

            val image1 = fileToMultipart(file1, "image1")
            val image2 = fileToMultipart(file2, "image2")
            val image3 = fileToMultipart(file3, "image3")

            val userId: RequestBody =
                "123".toRequestBody("text/plain".toMediaTypeOrNull())

            val response = withContext(Dispatchers.IO) {
                RetrofitInstance.api.uploadImages(
                    image1,
                    image2,
                    image3,
                    userId
                )
            }

            if (response.isSuccessful && response.body() != null) {

                val result = response.body()!!

                println("UPLOAD RESPONSE -> status=${result.status}, prediction=${result.prediction}")

                if (result.status == "success" && result.isEndoFile) {

                    val segmentResultsText = result.segmentResults.joinToString(",") { segment ->
                        "${segment.name} - ${segment.status}"
                    }

                    navController.navigate(
                        "${Screen.FileVerified.route}/${name ?: ""}/${email ?: ""}" +
                                "?confidence=${result.confidence}" +
                                "&prediction=${result.prediction}" +
                                "&recommendation=${result.recommendation}" +
                                "&imageUri1=${imageUri1 ?: ""}" +
                                "&imageUri2=${imageUri2 ?: ""}" +
                                "&imageUri3=${imageUri3 ?: ""}" +
                                "&heatmaps=${result.heatmaps.joinToString(",")}" +
                                "&segmentResults=$segmentResultsText"
                    )

                } else {

                    navController.navigate(
                        "${Screen.FileNotVerified.route}/${name ?: ""}/${email ?: ""}"
                    )
                }

            } else {

                resultMessage = "Upload failed"

            }

        } catch (e: Exception) {

            e.printStackTrace()
            resultMessage = "Network error"

        } finally {

            isLoading = false

        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (isLoading) {

            CircularProgressIndicator()

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Validating Image",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Verifying whether the uploaded image is an endodontic file...",
                style = MaterialTheme.typography.bodyMedium
            )

        } else {

            Text(text = resultMessage)

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navController.popBackStack() }) {
                Text("Go Back")
            }

        }

    }
}