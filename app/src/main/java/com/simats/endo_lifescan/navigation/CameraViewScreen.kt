package com.simats.endo_lifescan.navigation

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraViewScreen(
    navController: NavController,
    from: String?,
    name: String?,
    email: String?,
    imageUri1: String? = "",
    imageUri2: String? = ""
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraController = remember {
        LifecycleCameraController(context).apply {

            cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            imageCaptureFlashMode = ImageCapture.FLASH_MODE_OFF

            imageCaptureMode =
                ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
        }
    }
    LaunchedEffect(lifecycleOwner) {
        cameraController.bindToLifecycle(lifecycleOwner)
    }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    var isFlashOn by remember { mutableStateOf(false) }
    var isFrontCamera by remember { mutableStateOf(false) }
    var zoomLevel by remember { mutableStateOf(0.5f) }// default zoom for tip
    var isTipAligned by remember { mutableStateOf(false) }
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
            hasCameraPermission = it
        }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->

            uri?.let {

                val encodedName = Uri.encode(name ?: "")
                val encodedEmail = Uri.encode(email ?: "")
                val encodedUri = Uri.encode(uri.toString())

                val destination = when (from) {
                    "segment1" -> "${Screen.CaptureConfirmed.route}/$encodedName/$encodedEmail?imageUri1=$encodedUri"

                    "segment2" -> "${Screen.CaptureConfirmed2.route}/$encodedName/$encodedEmail?imageUri1=${Uri.encode(imageUri1 ?: "")}&imageUri2=$encodedUri"

                    "segment3" -> "${Screen.CaptureConfirmed3.route}/$encodedName/$encodedEmail?imageUri1=${Uri.encode(imageUri1 ?: "")}&imageUri2=${Uri.encode(imageUri2 ?: "")}&imageUri3=$encodedUri"

                    else -> Screen.Login.route
                }
                navController.navigate(destination)
            }

        }

    LaunchedEffect(true) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    fun takePhoto() {

        val photoName = "EndoLifeScan_${System.currentTimeMillis()}"

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, photoName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/EndoLifeScan")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                context.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        cameraController.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),

            object : ImageCapture.OnImageSavedCallback {

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {

                    val savedUri = Uri.encode(output.savedUri?.toString() ?: "")

                    val destination = when (from) {
                        "segment1" ->
                            "${Screen.CaptureConfirmed.route}/${Uri.encode(name ?: "")}/${Uri.encode(email ?: "")}?imageUri1=$savedUri"

                        "segment2" ->
                            "${Screen.CaptureConfirmed2.route}/${Uri.encode(name ?: "")}/${Uri.encode(email ?: "")}?imageUri1=${Uri.encode(imageUri1 ?: "")}&imageUri2=$savedUri"

                        "segment3" ->
                            "${Screen.CaptureConfirmed3.route}/${Uri.encode(name ?: "")}/${Uri.encode(email ?: "")}?imageUri1=${Uri.encode(imageUri1 ?: "")}&imageUri2=${Uri.encode(imageUri2 ?: "")}&imageUri3=$savedUri"

                        else -> Screen.Login.route
                    }

                    navController.navigate(destination)
                }

                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        context,
                        "Photo capture failed: ${exc.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    Scaffold(
        topBar = {

            TopAppBar(
                title = { Text("Camera") },

                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },

                actions = {

                    IconButton(onClick = {

                        isFlashOn = !isFlashOn

                        cameraController.imageCaptureFlashMode =
                            if (isFlashOn) ImageCapture.FLASH_MODE_ON
                            else ImageCapture.FLASH_MODE_OFF

                    }) {

                        Icon(
                            if (isFlashOn) Icons.Default.FlashOn else Icons.Default.FlashOff,
                            contentDescription = "Flash",
                            tint = Color.White
                        )
                    }

                    IconButton(onClick = {

                        isFrontCamera = !isFrontCamera

                        cameraController.cameraSelector =
                            if (isFrontCamera)
                                CameraSelector.DEFAULT_FRONT_CAMERA
                            else
                                CameraSelector.DEFAULT_BACK_CAMERA

                    }) {

                        Icon(
                            Icons.Default.Cameraswitch,
                            contentDescription = "Switch Camera",
                            tint = Color.White
                        )
                    }

                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingValues)
        ) {

            if (hasCameraPermission) {

                AndroidView(
                    factory = { ctx ->

                        PreviewView(ctx).apply {

                            controller = cameraController

                        }

                    },

                    modifier = Modifier.fillMaxSize()
                )
            }

            // 🎯 TIP ALIGNMENT GUIDE
            Canvas(modifier = Modifier.fillMaxSize()) {

                val radius = size.minDimension * 0.25f

                drawCircle(
                    color = Color.White,
                    radius = radius,
                    center = Offset(size.width / 2, size.height / 2),
                    style = Stroke(width = 4f)
                )
            }

            Text(
                text = if (isTipAligned) "Tip aligned ✓" else "Align file tip inside the circle",
                color = if (isTipAligned) Color.Green else Color.White,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 80.dp)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(32.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Slider(
                    value = zoomLevel,
                    onValueChange = {

                        zoomLevel = it
                        cameraController.cameraControl?.setLinearZoom(it)

                        isTipAligned = zoomLevel > 0.45f && zoomLevel < 0.65f

                    },
                    valueRange = 0f..1f
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    IconButton(
                        onClick = { galleryLauncher.launch("image/*") }
                    ) {

                        Icon(
                            Icons.Default.Photo,
                            contentDescription = "Gallery",
                            tint = Color.White
                        )
                    }

                    Button(
                        onClick = {

                            if (isTipAligned) {
                                takePhoto()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please align the tip in the center",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier.size(72.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        contentPadding = PaddingValues(0.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                                .border(2.dp, Color.Black, CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.width(48.dp))

                }

            }

        }

    }

}