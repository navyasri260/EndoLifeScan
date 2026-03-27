package com.simats.endo_lifescan.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController,
    name: String?,
    email: String?
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val prefs = context.getSharedPreferences("user_prefs", 0)
    val updatedName = prefs.getString("name", name ?: "User")

    Scaffold(

        bottomBar = {

            NavigationBar {

                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate(
                            "${Screen.Settings.route}/${name ?: ""}/${email ?: ""}"
                        )
                    },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") }
                )
            }
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF6F6F6))
                .padding(paddingValues)
                .padding(20.dp)
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Welcome,",
                fontSize = 22.sp
            )

            Text(
                text = updatedName ?: "User",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ------------------------
            // NEW SCAN CARD
            // ------------------------

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clickable {

                        navController.navigate(
                            "${Screen.ScanGuide.route}/${name ?: ""}/${email ?: ""}"
                        )

                    },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color(0xFFD7C4F0),
                                    Color(0xFFBCA6DB)
                                )
                            )
                        )
                        .padding(16.dp)
                ) {

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Icon(
                            Icons.Default.CameraAlt,
                            contentDescription = "Scan",
                            tint = Color(0xFF5E2B97),
                            modifier = Modifier.size(36.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {

                            Text(
                                "New Scan",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                "Analyze an endodontic file",
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ------------------------
            // AI ANALYSIS FEATURES
            // ------------------------

            Text(
                text = "AI Analysis Features",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            FeatureCard(
                title = "360° Segmented Capture",
                description = "Ensures complete surface inspection via 3 segments"
            )

            FeatureCard(
                title = "Safety-Focused Analysis",
                description = "Detects wear, cracks, tip damage, and deformation"
            )

            FeatureCard(
                title = "Breakage Probability",
                description = "AI-based risk estimation for safer reuse decisions"
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ------------------------
            // WARNING BOX
            // ------------------------

            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3CD))
            ) {

                Text(
                    text = "Decision Support ONLY: AI analysis supports clinical judgment and does not replace professional decision-making.",
                    modifier = Modifier.padding(16.dp),
                    color = Color(0xFF8A6D3B)
                )
            }
        }
    }
}

@Composable
fun FeatureCard(title: String, description: String) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(14.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                title,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                description,
                color = Color.Gray
            )
        }
    }
}