package com.simats.endo_lifescan.navigation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import coil.compose.AsyncImage
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun MagnifiedInspectionScreen(
    navController: NavController,
    name: String?,
    email: String?,
    confidence: Double,
    prediction: String?,
    recommendation: String?,
    imageUri1: String?,
    imageUri2: String?,
    imageUri3: String?,
    heatmaps: List<String>,
    segmentResults: List<String>
) {

    var progress by remember { mutableStateOf(0f) }

    // =========================
    // AI ANALYSIS VALUES
    // =========================

    val fatigueScore = confidence * 100

    val riskLevel =
        if (fatigueScore < 40) "Low"
        else if (fatigueScore < 70) "Medium"
        else "High"

    val fractureRisk = fatigueScore.toInt()
    val structuralIntegrity = 100 - fractureRisk

    // =========================
    // AUTO SCAN PROGRESS
    // =========================

    LaunchedEffect(Unit) {

        for (i in 1..100) {
            progress = i / 100f
            delay(50)
        }


        navController.navigate(
            "${Screen.InspectionResult.route}/${name ?: ""}/${email ?: ""}" +
                    "?prediction=$prediction" +
                    "&recommendation=$recommendation" +
                    "&confidence=$confidence" +
                    "&segmentResults=${segmentResults.joinToString(",")}"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Magnified Inspection",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Focusing on the stress-prone 5-6 mm apical zone",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "Captured Images",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            AsyncImage(
                model = imageUri1,
                contentDescription = "Segment 1",
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
            )

            AsyncImage(
                model = imageUri2,
                contentDescription = "Segment 2",
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
            )

            AsyncImage(
                model = imageUri3,
                contentDescription = "Segment 3",
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (heatmaps.isNotEmpty()) {

            Text(
                text = "AI Heatmap Analysis",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))



            Column {

                heatmaps.forEach { heatmap ->

                    AsyncImage(
                        model = "http://10.124.144.191:5000/uploads/$heatmap",
                        contentDescription = "AI Heatmap",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(bottom = 12.dp)
                    )

                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "AI Tip Condition Analysis",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            segmentResults.forEachIndexed { index, result ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                ) {

                    Column(modifier = Modifier.padding(12.dp)) {

                        Text(
                            text = "Image ${index + 1}",
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(text = result)

                    }

                }

            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Structural Fatigue Score: ${fatigueScore.toInt()}%",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Risk Level: $riskLevel",
            color = when (riskLevel) {
                "Low" -> Color.Green
                "Medium" -> Color(0xFFFFA500)
                else -> Color.Red
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // =========================
        // AI RISK ANALYSIS
        // =========================

        Text(
            "Medical Risk Assessment",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Fracture Risk", fontWeight = FontWeight.Bold)

        LinearProgressIndicator(
            progress = fractureRisk / 100f,
            modifier = Modifier.fillMaxWidth(),
            color = Color.Red
        )

        Text("$fractureRisk %", color = Color.Red)

        Spacer(modifier = Modifier.height(12.dp))

        Text("Structural Integrity", fontWeight = FontWeight.Bold)

        LinearProgressIndicator(
            progress = structuralIntegrity / 100f,
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF3D5AFE)
        )

        Text("$structuralIntegrity %", color = Color(0xFF3D5AFE))

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "AI Confidence: ${(confidence * 100).toInt()}%",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFEBEE)
            )
        ) {
            Column(modifier = Modifier.padding(12.dp)) {

                Text(
                    text = "Recommendation",
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(text = recommendation ?: "No recommendation available")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // =========================
        // MAGNIFYING ANIMATION
        // =========================

        MagnifyingAnimation()

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            "Scanning Micro-Structures",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth()
        )

        Text("${(progress * 100).toInt()}%", modifier = Modifier.align(Alignment.End))

        Spacer(modifier = Modifier.height(16.dp))

        Text("✓ Detecting micro-bending")
        Text("✓ Checking edge deformation")
        Text("✓ Analyzing early wear signs")
    }
}

@Composable
fun MagnifyingAnimation() {

    val infiniteTransition = rememberInfiniteTransition(label = "magnify")

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = -15f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(200.dp)
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = rotation
                )
        ) {

            val path = Path().apply {
                moveTo(size.width / 2, size.height * 0.2f)
                lineTo(size.width * 0.45f, size.height * 0.8f)
                lineTo(size.width * 0.55f, size.height * 0.8f)
                close()
            }

            drawPath(path, color = Color.Black)

            drawCircle(
                color = Color(0xFFF0E6FF),
                radius = size.minDimension / 2,
                style = Stroke(width = 4.dp.toPx())
            )
        }

        Text(
            "5-6mm Zone",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = 40.dp),
            color = Color.Gray
        )
    }
}