package com.simats.endo_lifescan.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.CreateAccount.route) { CreateAccountScreen(navController) }
        composable(Screen.AccountCreated.route) { AccountCreatedScreen(navController) }
        composable(Screen.WelcomeBack.route) { WelcomeBackScreen(navController) }

        composable(
            route = "${Screen.EnterPassword.route}/{email}/{name}",
            arguments = listOf(
                navArgument("email"){type=NavType.StringType},
                navArgument("name"){type=NavType.StringType},
                navArgument("prediction"){type=NavType.StringType; nullable=true},
                navArgument("recommendation"){type=NavType.StringType; nullable=true},
                navArgument("confidence"){type=NavType.StringType; nullable=true}
            )
        ) { backStackEntry ->
            EnterPasswordScreen(
                navController,
                backStackEntry.arguments?.getString("email"),
                backStackEntry.arguments?.getString("name")
            )
        }

        composable(Screen.ForgotPassword.route) { ForgotPasswordScreen(navController) }

        composable(
            route = "${Screen.Otp.route}/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            OtpScreen(navController, backStackEntry.arguments?.getString("email"))
        }

        composable(Screen.ChangePassword.route) { ChangePasswordScreen(navController) }

        // =========================
        // HOME
        // =========================

        composable(
            route = "${Screen.Home.route}/{name}/{email}",
            arguments = listOf(
                navArgument("name"){type=NavType.StringType; nullable=true},
                navArgument("email"){type=NavType.StringType; nullable=true}
            )
        ){backStackEntry->

            HomeScreen(
                navController,
                backStackEntry.arguments?.getString("name"),
                backStackEntry.arguments?.getString("email")
            )
        }


        // =========================
        // SCAN GUIDE FLOW
        // =========================

        composable("${Screen.ScanGuide.route}/{name}/{email}") {
            ScanGuideScreen(navController,it.arguments?.getString("name"),it.arguments?.getString("email"))
        }

        composable("${Screen.GuideClarity.route}/{name}/{email}") {
            GuideClarityScreen(navController,it.arguments?.getString("name"),it.arguments?.getString("email"))
        }

        composable("${Screen.GuideMistakes.route}/{name}/{email}") {
            GuideMistakesScreen(navController,it.arguments?.getString("name"),it.arguments?.getString("email"))
        }

        composable("${Screen.GuideLighting.route}/{name}/{email}") {
            GuideLightingScreen(navController,it.arguments?.getString("name"),it.arguments?.getString("email"))
        }

        composable("${Screen.GuideBackground.route}/{name}/{email}") {
            GuideBackgroundScreen(navController,it.arguments?.getString("name"),it.arguments?.getString("email"))
        }

        composable("${Screen.GuidePositioning.route}/{name}/{email}") {
            GuidePositioningScreen(navController,it.arguments?.getString("name"),it.arguments?.getString("email"))
        }

        composable("${Screen.ReadyToScan.route}/{name}/{email}") {
            ReadyToScanScreen(navController,it.arguments?.getString("name"),it.arguments?.getString("email"))
        }

        composable("${Screen.ThreeSixtyCapture.route}/{name}/{email}") {
            ThreeSixtyCaptureScreen(navController,it.arguments?.getString("name"),it.arguments?.getString("email"))
        }

        composable("${Screen.CameraSetup.route}/{name}/{email}") {
            CameraSetupScreen(navController,it.arguments?.getString("name"),it.arguments?.getString("email"))
        }

        composable(
            route = "${Screen.CameraView.route}?from={from}&name={name}&email={email}&imageUri1={imageUri1}&imageUri2={imageUri2}",
            arguments = listOf(
                navArgument("from"){ type = NavType.StringType },
                navArgument("name"){ type = NavType.StringType; nullable = true },
                navArgument("email"){ type = NavType.StringType; nullable = true },
                navArgument("imageUri1"){ type = NavType.StringType; nullable = true },
                navArgument("imageUri2"){ type = NavType.StringType; nullable = true }
            )
        ){ backStackEntry ->

            CameraViewScreen(
                navController,
                backStackEntry.arguments?.getString("from"),
                backStackEntry.arguments?.getString("name"),
                backStackEntry.arguments?.getString("email"),
                backStackEntry.arguments?.getString("imageUri1") ?: "",
                backStackEntry.arguments?.getString("imageUri2") ?: ""
            )
        }

        // =========================
        // IMAGE CAPTURE FLOW
        // =========================

        composable(
            route = "${Screen.CaptureSegment1.route}/{name}/{email}?imageUri1={imageUri1}",
            arguments = listOf(
                navArgument("name"){ type = NavType.StringType; nullable = true },
                navArgument("email"){ type = NavType.StringType; nullable = true },
                navArgument("imageUri1"){ type = NavType.StringType; nullable = true }
            )
        ) {

            CaptureSegment1Screen(
                navController = navController,
                name = it.arguments?.getString("name"),
                email = it.arguments?.getString("email"),
                imageUri1 = it.arguments?.getString("imageUri1") ?: ""
            )

        }

        composable(
            route = "${Screen.CaptureSegment2.route}/{name}/{email}?imageUri1={imageUri1}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType; nullable = true },
                navArgument("email") { type = NavType.StringType; nullable = true },
                navArgument("imageUri1") { type = NavType.StringType; nullable = true }
            )
        ) { backStackEntry ->

            CaptureSegment2Screen(
                navController = navController,
                name = backStackEntry.arguments?.getString("name"),
                email = backStackEntry.arguments?.getString("email"),
                imageUri1 = backStackEntry.arguments?.getString("imageUri1") ?: ""
            )
        }
        composable(
            route = "${Screen.CaptureSegment3.route}/{name}/{email}?imageUri1={imageUri1}&imageUri2={imageUri2}",
            arguments = listOf(
                navArgument("name"){ type = NavType.StringType; nullable = true },
                navArgument("email"){ type = NavType.StringType; nullable = true },
                navArgument("imageUri1"){ type = NavType.StringType; nullable = true },
                navArgument("imageUri2"){ type = NavType.StringType; nullable = true }
            )
        ) { backStackEntry ->

            CaptureSegment3Screen(
                navController = navController,
                name = backStackEntry.arguments?.getString("name"),
                email = backStackEntry.arguments?.getString("email"),
                imageUri1 = backStackEntry.arguments?.getString("imageUri1") ?: "",
                imageUri2 = backStackEntry.arguments?.getString("imageUri2") ?: ""
            )

        }
        composable(
            route = "${Screen.CaptureConfirmed.route}/{name}/{email}?imageUri1={imageUri1}",
            arguments = listOf(
                navArgument("name"){ type = NavType.StringType; nullable = true },
                navArgument("email"){ type = NavType.StringType; nullable = true },
                navArgument("imageUri1"){ type = NavType.StringType; nullable = true }
            )
        ) {
            CaptureConfirmedScreen(
                navController = navController,
                name = it.arguments?.getString("name"),
                email = it.arguments?.getString("email"),
                imageUri1 = it.arguments?.getString("imageUri1") ?: ""
            )
        }
        composable(
            route = "${Screen.CaptureConfirmed2.route}/{name}/{email}?imageUri1={imageUri1}&imageUri2={imageUri2}",
            arguments = listOf(
                navArgument("name"){ type = NavType.StringType; nullable = true },
                navArgument("email"){ type = NavType.StringType; nullable = true },
                navArgument("imageUri1"){ type = NavType.StringType; nullable = true },
                navArgument("imageUri2"){ type = NavType.StringType; nullable = true }
            )
        ) {

            CaptureConfirmed2Screen(
                navController = navController,
                name = it.arguments?.getString("name"),
                email = it.arguments?.getString("email"),
                imageUri1 = it.arguments?.getString("imageUri1") ?: "",
                imageUri2 = it.arguments?.getString("imageUri2") ?: ""
            )

        }
        composable(
            route = "${Screen.CaptureConfirmed3.route}/{name}/{email}?imageUri1={imageUri1}&imageUri2={imageUri2}&imageUri3={imageUri3}",
            arguments = listOf(
                navArgument("name"){ type = NavType.StringType; nullable = true },
                navArgument("email"){ type = NavType.StringType; nullable = true },
                navArgument("imageUri1"){ type = NavType.StringType; nullable = true },
                navArgument("imageUri2"){ type = NavType.StringType; nullable = true },
                navArgument("imageUri3"){ type = NavType.StringType; nullable = true }
            )
        ) {

            CaptureConfirmed3Screen(
                navController = navController,
                name = it.arguments?.getString("name"),
                email = it.arguments?.getString("email"),
                imageUri1 = it.arguments?.getString("imageUri1") ?: "",
                imageUri2 = it.arguments?.getString("imageUri2") ?: "",
                imageUri3 = it.arguments?.getString("imageUri3") ?: ""
            )

        }

        composable(
            route = "${Screen.ReviewImages.route}/{name}/{email}?imageUri1={imageUri1}&imageUri2={imageUri2}&imageUri3={imageUri3}"
        ) { backStackEntry ->

            ReviewImagesScreen(
                navController = navController,
                name = backStackEntry.arguments?.getString("name"),
                email = backStackEntry.arguments?.getString("email"),
                imageUri1 = backStackEntry.arguments?.getString("imageUri1") ?: "",
                imageUri2 = backStackEntry.arguments?.getString("imageUri2") ?: "",
                imageUri3 = backStackEntry.arguments?.getString("imageUri3") ?: ""
            )

        }

        composable("${Screen.CaptureComplete.route}/{name}/{email}") {
            CaptureCompleteScreen(
                navController,
                it.arguments?.getString("name"),
                it.arguments?.getString("email")
            )
        }

        // =========================
        // IMAGE VALIDATION
        // =========================

        composable(
            route = "${Screen.ValidatingImage.route}/{name}/{email}?imageUri1={imageUri1}&imageUri2={imageUri2}&imageUri3={imageUri3}",
            arguments = listOf(
                navArgument("name"){type=NavType.StringType;nullable=true},
                navArgument("email"){type=NavType.StringType;nullable=true},
                navArgument("imageUri1"){type=NavType.StringType;nullable=true},
                navArgument("imageUri2"){type=NavType.StringType;nullable=true},
                navArgument("imageUri3"){type=NavType.StringType;nullable=true}
            )
        ){backStackEntry->

            ValidatingImageScreen(
                navController,
                backStackEntry.arguments?.getString("name"),
                backStackEntry.arguments?.getString("email"),
                backStackEntry.arguments?.getString("imageUri1"),
                backStackEntry.arguments?.getString("imageUri2"),
                backStackEntry.arguments?.getString("imageUri3")
            )
        }
        // =========================
        // FILE NOT VERIFIED
        // =========================

        composable(
            route = "${Screen.FileNotVerified.route}/{name}/{email}",
            arguments = listOf(
                navArgument("name"){ type = NavType.StringType; nullable = true },
                navArgument("email"){ type = NavType.StringType; nullable = true }
            )
        ) { backStackEntry ->

            FileNotVerifiedScreen(
                navController,
                backStackEntry.arguments?.getString("name"),
                backStackEntry.arguments?.getString("email")
            )
        }

        // =========================
        // FILE VERIFIED
        // =========================

        composable(
            route="${Screen.FileVerified.route}/{name}/{email}?confidence={confidence}&prediction={prediction}&recommendation={recommendation}&imageUri1={imageUri1}&imageUri2={imageUri2}&imageUri3={imageUri3}&heatmaps={heatmaps}&segmentResults={segmentResults}",
            arguments=listOf(
                navArgument("name"){type=NavType.StringType;nullable=true},
                navArgument("email"){type=NavType.StringType;nullable=true},
                navArgument("confidence"){type=NavType.StringType;nullable=true},
                navArgument("prediction"){type=NavType.StringType;nullable=true},
                navArgument("recommendation"){type=NavType.StringType;nullable=true},
                navArgument("imageUri1"){type=NavType.StringType;nullable=true},
                navArgument("imageUri2"){type=NavType.StringType;nullable=true},
                navArgument("imageUri3"){type=NavType.StringType;nullable=true},
                navArgument("heatmaps"){type=NavType.StringType;nullable=true},
                navArgument("segmentResults"){type=NavType.StringType;nullable=true}
            )
        ){ backStackEntry ->

            FileVerifiedScreen(
                navController,
                backStackEntry.arguments?.getString("name"),
                backStackEntry.arguments?.getString("email"),
                backStackEntry.arguments?.getString("prediction"),
                backStackEntry.arguments?.getString("recommendation"),
                backStackEntry.arguments?.getString("confidence")?.toDoubleOrNull(),
                backStackEntry.arguments?.getString("imageUri1"),
                backStackEntry.arguments?.getString("imageUri2"),
                backStackEntry.arguments?.getString("imageUri3"),
                backStackEntry.arguments?.getString("heatmaps"),
                backStackEntry.arguments?.getString("segmentResults")
            )
        }
        // =========================
        // MAGNIFIED INSPECTION
        // =========================
        composable(
            route="${Screen.MagnifiedInspection.route}/{name}/{email}?confidence={confidence}&prediction={prediction}&recommendation={recommendation}&imageUri1={imageUri1}&imageUri2={imageUri2}&imageUri3={imageUri3}&heatmaps={heatmaps}&segmentResults={segmentResults}",
            arguments=listOf(
                navArgument("name"){type=NavType.StringType;nullable=true},
                navArgument("email"){type=NavType.StringType;nullable=true},
                navArgument("confidence"){type=NavType.StringType;nullable=true},
                navArgument("prediction"){type=NavType.StringType;nullable=true},
                navArgument("recommendation"){type=NavType.StringType;nullable=true},
                navArgument("imageUri1"){type=NavType.StringType;nullable=true},
                navArgument("imageUri2"){type=NavType.StringType;nullable=true},
                navArgument("imageUri3"){type=NavType.StringType;nullable=true},
                navArgument("heatmaps"){type=NavType.StringType;nullable=true},
                navArgument("segmentResults"){type=NavType.StringType;nullable=true}
            )
        ){ backStackEntry ->

            MagnifiedInspectionScreen(
                navController,
                backStackEntry.arguments?.getString("name"),
                backStackEntry.arguments?.getString("email"),
                backStackEntry.arguments?.getString("confidence")?.toDoubleOrNull() ?: 0.0,
                backStackEntry.arguments?.getString("prediction"),
                backStackEntry.arguments?.getString("recommendation"),
                backStackEntry.arguments?.getString("imageUri1"),
                backStackEntry.arguments?.getString("imageUri2"),
                backStackEntry.arguments?.getString("imageUri3"),
                backStackEntry.arguments?.getString("heatmaps")?.split(",") ?: emptyList(),
                backStackEntry.arguments?.getString("segmentResults")?.split(",") ?: emptyList()
            )
        }

        // =========================
        // SETTINGS
        // =========================

        composable("${Screen.Settings.route}/{name}/{email}") {
            SettingsScreen(navController,it.arguments?.getString("name"),it.arguments?.getString("email"))
        }

        composable("${Screen.EditProfile.route}/{name}/{email}") {
            EditProfileScreen(navController,it.arguments?.getString("name"),it.arguments?.getString("email"))
        }

        composable(Screen.Notifications.route) {
            NotificationsScreen(navController)
        }

        composable(Screen.LogoutConfirmation.route) {
            LogoutConfirmationScreen(navController)
        }

        composable(Screen.LoggedOut.route) {
            LoggedOutScreen(navController)
        }

        // =========================
        // INSPECTION RESULT
        // =========================

        composable(
            route = "${Screen.InspectionResult.route}/{name}/{email}?prediction={prediction}&recommendation={recommendation}&confidence={confidence}&segmentResults={segmentResults}",
            arguments = listOf(
                navArgument("name"){type=NavType.StringType;nullable=true},
                navArgument("email"){type=NavType.StringType;nullable=true},
                navArgument("prediction"){type=NavType.StringType;nullable=true},
                navArgument("recommendation"){type=NavType.StringType;nullable=true},
                navArgument("confidence"){type=NavType.StringType;nullable=true},
                navArgument("segmentResults"){type=NavType.StringType;nullable=true}
            )
        ){backStackEntry->

            InspectionResultScreen(
                navController,
                backStackEntry.arguments?.getString("name"),
                backStackEntry.arguments?.getString("email"),
                backStackEntry.arguments?.getString("segmentResults")?.split(",") ?: emptyList(),
                backStackEntry.arguments?.getString("prediction"),
                backStackEntry.arguments?.getString("recommendation"),
                backStackEntry.arguments?.getString("confidence")?.toDoubleOrNull() ?: 0.0
            )
        }

        // =========================
        // FINAL RESULT
        // =========================

        composable(
            route="${Screen.AnalysisComplete.route}/{email}/{name}?prediction={prediction}&recommendation={recommendation}&confidence={confidence}",
            arguments=listOf(
                navArgument("email"){type=NavType.StringType;nullable=true},
                navArgument("name"){type=NavType.StringType;nullable=true},
                navArgument("prediction"){type=NavType.StringType;nullable=true},
                navArgument("recommendation"){type=NavType.StringType;nullable=true},
                navArgument("confidence"){type=NavType.StringType;nullable=true}
            )
        ){backStackEntry->

            AnalysisCompleteScreen(
                navController,
                backStackEntry.arguments?.getString("email"),
                backStackEntry.arguments?.getString("name"),
                backStackEntry.arguments?.getString("prediction"),
                backStackEntry.arguments?.getString("recommendation"),
                backStackEntry.arguments?.getString("confidence")?.toDoubleOrNull()
            )
        }

        // =========================
        // RESULT SCREEN
        // =========================

        composable(
            route = "${Screen.Result.route}/{email}/{name}?prediction={prediction}&recommendation={recommendation}&confidence={confidence}",
            arguments = listOf(
                navArgument("email"){ type = NavType.StringType },
                navArgument("name"){ type = NavType.StringType },
                navArgument("prediction"){ type = NavType.StringType; nullable = true },
                navArgument("recommendation"){ type = NavType.StringType; nullable = true },
                navArgument("confidence"){ type = NavType.StringType; nullable = true }
            )
        ){ backStackEntry ->

            ResultScreen(
                navController = navController,
                email = backStackEntry.arguments?.getString("email"),
                name = backStackEntry.arguments?.getString("name"),
                prediction = backStackEntry.arguments?.getString("prediction") ?: "",
                recommendation = backStackEntry.arguments?.getString("recommendation") ?: "",
                confidence = backStackEntry.arguments?.getString("confidence")?.toDoubleOrNull() ?: 0.0
            )

        }

    }
}