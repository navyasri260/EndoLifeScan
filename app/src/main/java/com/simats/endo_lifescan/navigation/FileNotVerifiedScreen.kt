package com.simats.endo_lifescan.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
fun FileNotVerifiedScreen(
    navController: NavController,
    name: String?,
    email: String?
) {

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
                .background(Color(0xFFFFEBEE)),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Not Verified",
                modifier = Modifier.size(60.dp),
                tint = Color(0xFFD32F2F)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Verification Failed",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFFD32F2F)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "The given images are not endodontic files. Please scan again.",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(64.dp))

        Button(
            onClick = {
                navController.navigate("${Screen.ThreeSixtyCapture.route}/$name/$email")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3D5AFE)),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
        ) {

            Text(
                "Scan Again",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}