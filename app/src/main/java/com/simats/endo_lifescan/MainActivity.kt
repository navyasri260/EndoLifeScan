package com.simats.endo_lifescan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.simats.endo_lifescan.navigation.Navigation
import com.simats.endo_lifescan.ui.theme.Endo_lifeScanTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Endo_lifeScanTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Navigation()   // ✅ Now resolved
                }
            }
        }
    }
}