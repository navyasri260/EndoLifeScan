package com.simats.endo_lifescan.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.text.style.TextAlign

@Composable
fun FileVerifiedScreen(
    navController: NavController,
    name: String?,
    email: String?,
    prediction: String?,
    recommendation: String?,
    confidence: Double?,
    imageUri1: String?,
    imageUri2: String?,
    imageUri3: String?,
    heatmaps: String?,
    segmentResults: String?
) {

    val safeConfidence = confidence ?: 0.0
    val fractureRisk = (safeConfidence * 100).toInt()
    val structuralIntegrity = 100 - fractureRisk

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color(0xFFE8F5E9)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Verified",
                modifier = Modifier.size(60.dp),
                tint = Color(0xFF4CAF50)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "File Verified",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF2E7D32)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Endodontic file detected successfully.\nReady for deep structural analysis.",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Medical Risk Assessment",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Fracture Risk", fontWeight = FontWeight.Bold)

        LinearProgressIndicator(
            progress = fractureRisk / 100f,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = Color.Red
        )

        Text("$fractureRisk%", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Structural Integrity", fontWeight = FontWeight.Bold)

        LinearProgressIndicator(
            progress = structuralIntegrity / 100f,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = Color(0xFF3D5AFE)
        )

        Text("$structuralIntegrity%", color = Color(0xFF3D5AFE))

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "AI Confidence: ${(safeConfidence * 100).toInt()}%",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                navController.navigate(
                    "${Screen.MagnifiedInspection.route}/${name ?: ""}/${email ?: ""}" +
                            "?confidence=$safeConfidence" +
                            "&prediction=$prediction" +
                            "&recommendation=$recommendation" +
                            "&imageUri1=$imageUri1" +
                            "&imageUri2=$imageUri2" +
                            "&imageUri3=$imageUri3" +
                            "&heatmaps=$heatmaps" +
                            "&segmentResults=$segmentResults"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3D5AFE)),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
        ) {

            Text(
                "Start Magnified Inspection",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}