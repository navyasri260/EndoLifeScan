package com.simats.endo_lifescan.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.endo_lifescan.R

@Composable
fun LoginScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)   // ✅ changed to white background
    ) {

        // LEFT LOGO
        Image(
            painter = painterResource(R.drawable.simats1),
            contentDescription = "simats1",
            modifier = Modifier
                .size(140.dp)
                .align(Alignment.TopStart)
                .padding(16.dp),
            contentScale = ContentScale.Fit
        )

        // RIGHT LOGO
        Image(
            painter = painterResource(R.drawable.simats2),
            contentDescription = "simats2",
            modifier = Modifier
                .size(140.dp)
                .align(Alignment.TopEnd)
                .padding(16.dp),
            contentScale = ContentScale.Fit
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(120.dp))

            AppLogo()

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Welcome to EndoLifeScan",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1D2D44)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Advanced AI-powered analysis for endodontic file safety and lifecycle management.",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = { navController.navigate(Screen.WelcomeBack.route) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3D5AFE)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Log In",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = { navController.navigate(Screen.CreateAccount.route) }
            ) {
                Text(
                    text = "Create Account",
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "v1.0.0 • Clinical Use Only",
                fontSize = 12.sp,
                color = Color.LightGray,textAlign = TextAlign.Center
            )
            Text(
                text = "Powered by SIMATS Engineering",
                fontSize = 12.sp,
                color = Color.LightGray, textAlign = TextAlign.Center
            )
        }
    }
}