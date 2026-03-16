package com.simats.endo_lifescan.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
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
fun DetailedAnalysisSafeScreen(navController: NavController, email: String?, name: String?) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detailed Analysis") },
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
            Text("Endodontic File Analysis", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text("2/7/2026, 9:03:15 AM", color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            SafeToReuseCard()
            Spacer(modifier = Modifier.height(24.dp))
            Text("360° Segmented Analysis", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                SegmentResultCard(segment = "Seg 1")
                SegmentResultCard(segment = "Seg 2")
                SegmentResultCard(segment = "Seg 3")
            }
            Spacer(modifier = Modifier.height(24.dp))
            // ... more sections for Magnified Edge Inspection, AI Reasoning, etc.

            Spacer(modifier = Modifier.weight(1f, fill = false))
            Button(
                onClick = { navController.navigate("${Screen.Home.route}/$name/$email") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF663399))
            ) {
                Text("Return to Home")
            }
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
private fun SegmentResultCard(segment: String, hasError: Boolean = false) {
    Card(colors = CardDefaults.cardColors(containerColor = if (hasError) Color(0xFFFFEBEE) else Color(0xFFF5F5F5))) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(segment, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(if (hasError) "! Error" else "✓ Analyzed", color = if (hasError) Color.Red else Color.Green)
        }
    }
}
