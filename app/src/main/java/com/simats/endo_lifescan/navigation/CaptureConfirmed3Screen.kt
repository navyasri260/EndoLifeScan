package com.simats.endo_lifescan.navigation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaptureConfirmed3Screen(
    navController: NavController,
    name: String?,
    email: String?,
    imageUri1: String = "",
    imageUri2: String = "",
    imageUri3: String = ""
) {
    Scaffold {
        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Capture Confirmed", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(48.dp))

            Segment3CapturedDiagram()

            Spacer(modifier = Modifier.height(48.dp))
            Text("Segment 3 Captured", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("3 of 3 segments complete", fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = { navController.navigate("${Screen.ReviewImages.route}/$name/$email?imageUri1=$imageUri1&imageUri2=$imageUri2&imageUri3=$imageUri3") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF663399))
            ) {
                Text("Review All Segments", fontSize = 16.sp)
            }
        }
    }
}

@Composable
private fun Segment3CapturedDiagram() {
    Canvas(modifier = Modifier.size(150.dp)) {
        val strokeWidth = 2.dp.toPx()
        val outlineColor = Color.DarkGray

        // Filled segments
        drawArc(
            color = Color(0xFF8E44AD),
            startAngle = -150f,
            sweepAngle = 120f,
            useCenter = true
        )
        drawArc(
            color = Color(0xFF8E44AD),
            startAngle = -30f,
            sweepAngle = 120f,
            useCenter = true
        )
        drawArc(
            color = Color(0xFF8E44AD),
            startAngle = 90f,
            sweepAngle = 120f,
            useCenter = true
        )

        // Outline for all segments
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
