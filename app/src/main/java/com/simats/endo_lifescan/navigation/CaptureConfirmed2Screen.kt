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
import android.net.Uri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaptureConfirmed2Screen(
    navController: NavController,
    name: String?,
    email: String?,
    imageUri1: String = "",
    imageUri2: String = ""
)
{
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

            Segment2CapturedDiagram()

            Spacer(modifier = Modifier.height(48.dp))
            Text("Segment 2 Captured", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("2 of 3 segments complete", fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = {

                    val encodedImage1 = Uri.encode(imageUri1)

                    navController.navigate(
                        "${Screen.CaptureSegment3.route}/$name/$email?imageUri1=$encodedImage1&imageUri2=${Uri.encode(imageUri2)}"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF663399))
            ) {
                Text("Continue to Segment 3", fontSize = 16.sp)
            }
        }
    }
}

@Composable
private fun Segment2CapturedDiagram() {
    Canvas(modifier = Modifier.size(150.dp)) {
        val strokeWidth = 2.dp.toPx()
        val outlineColor = Color.DarkGray

        // Filled segments 1 & 2
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

        // Background segment 3
        drawArc(
            color = Color(0xFFEEEEEE),
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
