package com.pdfforge.app.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.pdfforge.app.updater.GithubRelease

@Composable
fun UpdateDialog(
    release: GithubRelease,
    onConfirm: (apkUrl: String) -> Unit,
    onDismiss: () -> Unit
) {
    val apkAsset = release.assets.firstOrNull { it.name.endsWith(".apk") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Update Available") },
        text = { 
            Text(text = "Version ${release.tag_name} is available. Do you want to download and install it?\n\nRelease Notes:\n${release.body}") 
        },
        confirmButton = {
            if (apkAsset != null) {
                TextButton(onClick = { onConfirm(apkAsset.browser_download_url) }) {
                    Text("Download Update")
                }
            } else {
                TextButton(onClick = onDismiss) {
                    Text("No APK found")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Later")
            }
        }
    )
}
