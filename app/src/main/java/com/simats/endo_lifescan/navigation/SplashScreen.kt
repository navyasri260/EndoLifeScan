package com.simats.endo_lifescan.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(3000L)
        navController.navigate("login")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .gradientBackground(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppLogo()
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "EndoLifeScan",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1D2D44)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "v1.0.0",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}
