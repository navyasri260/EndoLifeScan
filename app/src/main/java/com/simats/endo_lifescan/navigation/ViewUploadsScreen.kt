package com.simats.endo_lifescan.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel

import com.simats.endo_lifescan.viewmodel.UploadViewModel
import com.simats.endo_lifescan.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewUploadsScreen(
    navController: NavController,
    name: String?,
    email: String?
) {

    // 🔹 Upload ViewModel
    val uploadViewModel: UploadViewModel = viewModel()

    // 🔹 Observe API result
    val uploadResult by uploadViewModel.uploadResult.collectAsState()

    // 🔹 Navigate when AI result arrives
    uploadResult?.let { result ->

        navController.navigate(
            "result/${result.prediction}/${result.recommendation}"
        )

        uploadViewModel.clearResult()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("View Uploaded Images") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3F5F9))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            UploadedImageCard(
                title = "Segment 1",
                placeholderRes = R.drawable.ic_launcher_foreground
            )

            Spacer(modifier = Modifier.height(16.dp))

            UploadedImageCard(
                title = "Segment 2",
                placeholderRes = R.drawable.ic_launcher_foreground
            )

            Spacer(modifier = Modifier.height(16.dp))

            UploadedImageCard(
                title = "Segment 3",
                placeholderRes = R.drawable.ic_launcher_foreground
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    navController.navigate(
                        "${Screen.ValidatingImage.route}/$name/$email"
                    )

                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3D5AFE)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {

                Text(
                    "Next",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun UploadedImageCard(
    title: String,
    placeholderRes: Int
) {

    Column {

        Text(
            title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            shape = RoundedCornerShape(12.dp)
        ) {

            Image(
                painter = painterResource(id = placeholderRes),
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )
        }
    }
}