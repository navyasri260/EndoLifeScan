package com.simats.endo_lifescan.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun RiskAssessmentScreen(navController: NavController, name: String?, email: String?) {
    // Senior Architect: In production, these values would come from a ViewModel 
    // populated by the ValidatingImageScreen API response.
    val fractureRisk = 0.85f // Example high risk
    val structuralIntegrity = 0.15f
    val statusColor = if (fractureRisk > 0.7f) Color(0xFFD32F2F) else Color(0xFF388E3C)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(24.dp)
    ) {
        Text(
            "Medical Risk Assessment",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            "AI-Driven Structural Analysis Result",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Risk Bars
        RiskFactorBar(label = "Fracture Risk", score = fractureRisk, color = statusColor)
        Spacer(modifier = Modifier.height(16.dp))
        RiskFactorBar(label = "Structural Integrity", score = structuralIntegrity, color = Color(0xFF3D5AFE))

        Spacer(modifier = Modifier.height(48.dp))

        // Architect Recommendation Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = statusColor.copy(alpha = 0.1f)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (fractureRisk > 0.7f) "FINAL RESULT" else "FINAL RESULT",
                    fontWeight = FontWeight.ExtraBold,
                    color = statusColor,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (fractureRisk > 0.7f) "High risk of separation detected. Do not reuse this file." 
                           else "File meets safety parameters for reuse.",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = statusColor
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { 
                if (fractureRisk > 0.7f) {
                    navController.navigate("${Screen.AnalysisResultsDiscard.route}/$name/$email")
                } else {
                    navController.navigate("${Screen.AnalysisResultsSafe.route}/$name/$email")
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3D5AFE)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("View Detailed Findings", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun RiskFactorBar(label: String, score: Float, color: Color) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(label, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("${(score * 100).toInt()}%", fontWeight = FontWeight.Bold, color = color)
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { score },
            modifier = Modifier.fillMaxWidth().height(12.dp).clip(RoundedCornerShape(6.dp)),
            color = color,
            trackColor = color.copy(alpha = 0.1f)
        )
    }
}
