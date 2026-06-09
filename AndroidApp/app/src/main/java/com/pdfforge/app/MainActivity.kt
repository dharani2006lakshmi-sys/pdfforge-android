package com.pdfforge.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.pdfforge.app.ui.components.UpdateDialog
import com.pdfforge.app.ui.screens.DashboardScreen
import com.pdfforge.app.ui.theme.PDFforgeTheme
import com.pdfforge.app.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        
        setContent {
            val updateRelease by viewModel.updateAvailable.collectAsState()

            PDFforgeTheme {
                DashboardScreen(
                    onToolClick = { tool ->
                        // Navigation to tool detail will happen here in next phase
                    }
                )

                updateRelease?.let { release ->
                    UpdateDialog(
                        release = release,
                        onConfirm = { apkUrl ->
                            viewModel.downloadUpdate(apkUrl, "PDFforge_${release.tag_name}.apk")
                        },
                        onDismiss = {
                            viewModel.dismissUpdate()
                        }
                    )
                }
            }
        }
    }
}
