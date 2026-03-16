package com.simats.endo_lifescan.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewImagesScreen(
    navController: NavController,
    name: String?,
    email: String?,
    imageUri1: String?,
    imageUri2: String?,
    imageUri3: String?
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Review Images") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
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

            ImageReviewCard("Segment1 Image", imageUri1)
            Spacer(modifier = Modifier.height(16.dp))

            ImageReviewCard("Segment2 Image", imageUri2)
            Spacer(modifier = Modifier.height(16.dp))

            ImageReviewCard("Segment3 Image", imageUri3)
            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Quality Check",
                    tint = Color(0xFF3D5AFE)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "All images meet the quality standards for AI analysis.",
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    // Senior Architect: Navigate ONLY.
                    // The actual upload happens in ValidatingImageScreen.
                    navController.navigate(
                        "${Screen.ValidatingImage.route}/$name/$email" +
                                "?imageUri1=$imageUri1" +
                                "&imageUri2=$imageUri2" +
                                "&imageUri3=$imageUri3"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3D5AFE)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Analyze File",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun ImageReviewCard(title: String, imageUri: String?) {
    Column {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Card(shape = RoundedCornerShape(12.dp)) {
            AsyncImage(
                model = imageUri,
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
