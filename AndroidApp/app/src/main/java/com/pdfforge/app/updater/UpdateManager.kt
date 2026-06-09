package com.pdfforge.app.updater

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.pdfforge.app.api.GithubService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val githubService: GithubService
) {
    // Configuration - user can change these to match their github repository
    private val owner = "dharani2006lakshmi-sys" 
    private val repo = "pdfforge-android"
    private val currentVersion = "v1.0.0"

    suspend fun checkForUpdate(): GithubRelease? = withContext(Dispatchers.IO) {
        try {
            val release = githubService.getLatestRelease(owner, repo)
            if (release.tag_name != currentVersion) {
                return@withContext release
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext null
    }

    fun downloadAndInstallUpdate(apkUrl: String, fileName: String) {
        val request = DownloadManager.Request(Uri.parse(apkUrl))
            .setTitle("Downloading PDFforge Update")
            .setDescription("Version $fileName")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }
}
