package com.simats.endo_lifescan.navigation

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaptureSegment2Screen(
    navController: NavController,
    name: String?,
    email: String?,
    imageUri1: String = ""
){
    val context = LocalContext.current

    var galleryPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
            galleryPermission = it
        }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val encodedImage1 = Uri.encode(imageUri1)
                val encodedImage2 = Uri.encode(uri.toString())

                navController.navigate(
                    "${Screen.CaptureConfirmed2.route}/$name/$email?imageUri1=$encodedImage1&imageUri2=$encodedImage2"
                )
            }
        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Capture Segment 2") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(32.dp))

            AnimatedSegment2Indicator()

            Spacer(modifier = Modifier.height(32.dp))

            Text("Segment 2 of 3", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Slightly rotate the file (120°) and capture the next angle",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(48.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable {
                    val encodedImage1 = Uri.encode(imageUri1)

                    val encodedName = Uri.encode(name ?: "")
                    val encodedEmail = Uri.encode(email ?: "")

                    navController.navigate(
                        "${Screen.CameraView.route}?from=segment2&name=$encodedName&email=$encodedEmail&imageUri1=$encodedImage1"
                    )
                }
            ) {

                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Use Camera",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF0E6FF))
                        .padding(16.dp),
                    tint = Color(0xFF663399)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("Use Camera", fontWeight = FontWeight.Bold)

                Text("Capture directly", color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable {

                    if (!galleryPermission) {
                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                    } else {
                        galleryLauncher.launch("image/*")
                    }

                }
            ) {

                Icon(
                    imageVector = Icons.Default.Photo,
                    contentDescription = "Upload From Gallery",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE3F2FD))
                        .padding(16.dp),
                    tint = Color(0xFF1E88E5)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("Upload From Gallery", fontWeight = FontWeight.Bold)

                Text("Select image", color = Color.Gray)
            }
        }
    }
}

@Composable
private fun AnimatedSegment2Indicator() {

    var animate by remember { mutableStateOf(false) }

    val sweepAngle by animateFloatAsState(
        targetValue = if (animate) 120f else 0f,
        animationSpec = tween(durationMillis = 1500, delayMillis = 300),
        label = "segment2Animation"
    )

    LaunchedEffect(Unit) {
        animate = true
    }

    Canvas(modifier = Modifier.size(150.dp)) {

        val strokeWidth = 2.dp.toPx()
        val outlineColor = Color.DarkGray

        drawArc(
            color = Color(0xFF8E44AD),
            startAngle = -150f,
            sweepAngle = 120f,
            useCenter = true
        )

        drawArc(
            color = Color(0xFFEEEEEE),
            startAngle = 90f,
            sweepAngle = 120f,
            useCenter = true
        )

        drawArc(
            color = Color(0xFF8E44AD),
            startAngle = -30f,
            sweepAngle = sweepAngle,
            useCenter = true
        )

        drawArc(
            color = outlineColor,
            startAngle = -150f,
            sweepAngle = 120f,
            useCenter = true,
            style = Stroke(width = strokeWidth)
        )

        drawArc(
            color = outlineColor,
            startAngle = -30f,
            sweepAngle = 120f,
            useCenter = true,
            style = Stroke(width = strokeWidth)
        )

        drawArc(
            color = outlineColor,
            startAngle = 90f,
            sweepAngle = 120f,
            useCenter = true,
            style = Stroke(width = strokeWidth)
        )
    }
}