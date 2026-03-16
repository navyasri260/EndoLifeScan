package com.simats.endo_lifescan.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun InspectionResultScreen(
    navController: NavController,
    name: String?,
    email: String?,
    segmentResults: List<String>,
    prediction: String?,
    recommendation: String?,
    confidence: Double
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Inspection Result",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        segmentResults.forEachIndexed { index, result ->

            val color = when {
                result.contains("safe", true) -> Color.Green
                result.contains("deformation", true) -> Color(0xFFFFA500)
                result.contains("chipped", true) -> Color.Red
                else -> Color.Gray
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(color, CircleShape)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {

                        Text(
                            text = "Image ${index + 1}",
                            fontWeight = FontWeight.Bold
                        )

                        Text(text = result)

                    }

                }

            }

        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                navController.navigate(
                    "${Screen.AnalysisComplete.route}/${email ?: ""}/${name ?: ""}" +
                            "?prediction=$prediction" +
                            "&recommendation=$recommendation" +
                            "&confidence=$confidence"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            Text("View Full Analysis")
        }

    }
}
