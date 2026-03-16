package com.simats.endo_lifescan.navigation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThreeSixtyCaptureScreen(navController: NavController, name: String?, email: String?) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("360° Capture Guide") },
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
            Spacer(modifier = Modifier.height(16.dp))
            Text("How to Capture the File", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Rotate the file for each of the 3 segments to ensure complete surface inspection.",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))

            AnimatedCaptureGuide()

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { navController.navigate("${Screen.CameraSetup.route}/$name/$email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF663399))
            ) {
                Text("Begin 360° Capture", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun AnimatedCaptureGuide() {
    val transition = rememberInfiniteTransition(label = "capture_guide_animation")
    val currentSegment by transition.animateFloat(
        initialValue = 1f,
        targetValue = 4f, // 1, 2, 3 and then resets
        animationSpec = infiniteRepeatable(
            animation = tween(4500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "segment_stepper"
    )

    val segment = currentSegment.toInt()
    val rotationAngle = when(segment) {
        1 -> 0f
        2 -> 120f
        3 -> 240f
        else -> 0f
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Canvas(modifier = Modifier.size(150.dp)) {
            val center = this.center
            
            // Draw the background circle guide
            drawCircle(color = Color.LightGray, style = Stroke(width = 2.dp.toPx()), radius = size.minDimension / 2.5f)

            rotate(degrees = rotationAngle, pivot = center) {
                // Draw a simplified endodontic file shape
                drawRect(
                    color = Color(0xFF3D5AFE),
                    topLeft = Offset(center.x - 10.dp.toPx(), center.y - (size.minDimension / 3)),
                    size = androidx.compose.ui.geometry.Size(20.dp.toPx(), size.minDimension / 1.5f)
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Capture Segment $segment",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1D2D44)
        )
    }
}
