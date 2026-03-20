package com.simats.endo_lifescan.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ResultScreen(
    navController: NavController,
    email: String?,
    name: String?,
    prediction: String,
    recommendation: String,
    confidence: Double
) {

    val fractureRisk = (confidence * 100).toInt()
    val structuralIntegrity = 100 - fractureRisk

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Medical Risk Assessment",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "AI-Driven Structural Analysis Result",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(40.dp))

        LinearProgressIndicator(
            progress = fractureRisk / 100f,
            color = Color(0xFF1565C0),
            modifier = Modifier.fillMaxWidth()
        )

        Text("$fractureRisk%", color = Color(0xFF1565C0))

        Spacer(modifier = Modifier.height(24.dp))

        Text("Structural Integrity", fontWeight = FontWeight.Bold)
        LinearProgressIndicator(
            progress = structuralIntegrity / 100f,
            color = Color.Blue,
            modifier = Modifier.fillMaxWidth()
        )

        Text("$structuralIntegrity%", color = Color.Blue)

        Spacer(modifier = Modifier.height(40.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE3F2FD)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "FINAL RESULT",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1565C0)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(recommendation)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "AI Confidence: ${(confidence * 100).toInt()}%",
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                navController.navigate("${Screen.Home.route}/$name/$email"){
                    popUpTo(Screen.Home.route){ inclusive = false }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF663399))
        ) {
            Text("Return to Home")
        }
    }
}