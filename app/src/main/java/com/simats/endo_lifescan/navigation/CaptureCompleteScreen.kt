package com.simats.endo_lifescan.navigation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaptureCompleteScreen(navController: NavController, name: String?, email: String?) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("360° Capture Complete") },
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
            AnimatedCircleIndicator()
            Spacer(modifier = Modifier.height(48.dp))

            SegmentStatus(text = "Segment 1")
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            SegmentStatus(text = "Segment 2")
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            SegmentStatus(text = "Segment 3")

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { navController.navigate("${Screen.ValidatingImage.route}/$name/$email") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF663399))
            ) {
                Text("Verify & Analyze", fontSize = 16.sp)
            }
            TextButton(onClick = { navController.navigate("${Screen.ThreeSixtyCapture.route}/$name/$email") }) {
                Icon(Icons.Default.Refresh, contentDescription = "Retake", tint = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Retake Segments", color = Color.Gray)
            }
        }
    }
}

@Composable
fun AnimatedCircleIndicator() {
    var animate by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (animate) 1f else 0f,
        animationSpec = tween(1500, 300),
        label = "progressAnimation"
    )

    LaunchedEffect(Unit) {
        animate = true
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(150.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = Color(0xFF663399),
                startAngle = -90f,
                sweepAngle = 360 * progress,
                useCenter = false,
                style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Text("100%", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF663399))
    }
}

@Composable
fun SegmentStatus(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text, fontSize = 16.sp, modifier = Modifier.weight(1f))
        Icon(Icons.Default.Check, contentDescription = "Ready", tint = Color(0xFF4CAF50))
        Spacer(modifier = Modifier.width(8.dp))
        Text("Ready", color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
    }
}
