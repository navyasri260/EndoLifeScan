package com.simats.endo_lifescan.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisResultsDiscardScreen(navController: NavController, email: String?) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Analysis Results") },
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
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            DiscardCard()
            Spacer(modifier = Modifier.height(16.dp))
            AiExplanationCard(text = "Structural defects and significant wear detected, indicating a high risk of fracture.")
            Spacer(modifier = Modifier.height(24.dp))
            Text("Breakage Probability", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate("${Screen.DetailedAnalysisDiscard.route}/$email") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF663399))
            ) {
                Text("View Detailed Analysis")
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text("Start New Scan", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            AnalysisParameter(parameter = "Tip Integrity", finding = "Deformed", isGood = false)
            AnalysisParameter(parameter = "Surface Wear", finding = "Excessive", isGood = false)
            AnalysisParameter(parameter = "Bending", finding = "Detected", isGood = false)
            AnalysisParameter(parameter = "Cracks/Fractures", finding = "Detected", isGood = false)
            AnalysisParameter(parameter = "Metal Fatigue", finding = "High", isGood = false)
            Spacer(modifier = Modifier.height(24.dp))
            InfoCard(text = "This analysis supports clinical judgment. Final reuse decisions should be made by the clinician based on professional assessment.")
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun DiscardCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.Red)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Icon(Icons.Default.Warning, contentDescription = "Discard", tint = Color.Red, modifier = Modifier.size(48.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Discard File", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text("Confidence: High", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(16.dp))
                Text("Risk: High", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun AiExplanationCard(text: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Info, contentDescription = "AI Info", tint = Color.Gray)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text, fontSize = 14.sp)
        }
    }
}

@Composable
private fun AnalysisParameter(parameter: String, finding: String, isGood: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(parameter, fontSize = 16.sp)
        Text(
            finding,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = if (isGood) Color(0xFF4CAF50) else Color.Red
        )
    }
}

@Composable
private fun InfoCard(text: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Info, contentDescription = "Clinical Judgment", tint = Color(0xFF1976D2))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text, fontSize = 12.sp, color = Color(0xFF1976D2))
        }
    }
}
