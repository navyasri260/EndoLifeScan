package com.simats.endo_lifescan.navigation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun AnalysisCompleteScreen(
    navController: NavController,
    email: String?,
    name: String?,
    prediction: String?,
    recommendation: String?,
    confidence: Double?
) {
    var progress by remember { mutableStateOf(0f) }
    var step1Complete by remember { mutableStateOf(false) }
    var step2Complete by remember { mutableStateOf(false) }
    var step3Complete by remember { mutableStateOf(false) }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000),
        label = "AnalysisProgress"
    )

    LaunchedEffect(Unit) {
        // Total animation time: 3 seconds
        progress = 0.33f
        delay(1000)
        step1Complete = true
        progress = 0.66f
        delay(1000)
        step2Complete = true
        progress = 1f
        delay(1000)
        step3Complete = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text("Analysis Complete", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        LinearProgressIndicator(progress = animatedProgress, modifier = Modifier.fillMaxWidth())
        Text("${(animatedProgress * 100).toInt()}%", modifier = Modifier.align(Alignment.End))
        Spacer(modifier = Modifier.height(32.dp))
        
        AnalysisResultItem(text = "Structure analysis complete", isComplete = step1Complete)
        AnalysisResultItem(text = "Pattern recognition complete", isComplete = step2Complete)

        
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { navController.navigate(
                "${Screen.Result.route}/$email/$name" +
                        "?prediction=$prediction" +
                        "&recommendation=$recommendation" +
                        "&confidence=$confidence"
            ) },
            modifier = Modifier.fillMaxWidth(),
            enabled = step2Complete, // Button is enabled only when analysis is fully complete
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF663399))
        ) {
            Text("View Results", fontSize = 16.sp)
        }
    }
}

@Composable
fun AnalysisResultItem(text: String, isComplete: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        if (isComplete) {
            Icon(Icons.Default.Check, contentDescription = "Complete", tint = Color(0xFF4CAF50))
        } else {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                strokeWidth = 2.dp,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, fontSize = 16.sp, color = if(isComplete) Color.Black else Color.Gray)
    }
}
