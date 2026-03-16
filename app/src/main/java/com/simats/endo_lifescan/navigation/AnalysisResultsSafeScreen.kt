package com.simats.endo_lifescan.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
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
fun AnalysisResultsSafeScreen(navController: NavController, email: String?, name: String?) {
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
            SafeToReuseCard()
            Spacer(modifier = Modifier.height(16.dp))
            AiExplanationCard(text = "No significant wear patterns or structural defects detected across all 360° segments.")
            Spacer(modifier = Modifier.height(24.dp))
            Text("Breakage Probability", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate("${Screen.DetailedAnalysisSafe.route}/$email/$name") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF663399))
            ) {
                Text("View Detailed Analysis")
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text("Start New Scan", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            AnalysisParameter(parameter = "Tip Integrity", finding = "Good", isGood = true)
            AnalysisParameter(parameter = "Surface Wear", finding = "Minimal", isGood = true)
            AnalysisParameter(parameter = "Bending", finding = "None detected", isGood = true)
            AnalysisParameter(parameter = "Cracks/Fractures", finding = "None detected", isGood = true)
            AnalysisParameter(parameter = "Metal Fatigue", finding = "Low", isGood = true)
            Spacer(modifier = Modifier.height(24.dp))
            InfoCard(text = "This analysis supports clinical judgment. Final reuse decisions should be made by the clinician based on professional assessment.")
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SafeToReuseCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.Green)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Icon(Icons.Default.CheckCircle, contentDescription = "Safe", tint = Color.Green, modifier = Modifier.size(48.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Safe to reuse", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Green)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text("Confidence: High", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(16.dp))
                Text("Risk: Low", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun AnalysisParameter(parameter: String, finding: String, isGood: Boolean) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(parameter, modifier = Modifier.weight(1f))
        Card(
            colors = CardDefaults.cardColors(containerColor = if (isGood) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)),
        ) {
            Text(finding, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp), color = if(isGood) Color.Green else Color.Red)
        }
    }
}

@Composable
private fun AiExplanationCard(text: String) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFF0E6FF))){
        Row(modifier = Modifier.padding(16.dp)){
            Icon(Icons.Default.Info, contentDescription = "AI", tint = Color(0xFF663399))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text)
        }
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
