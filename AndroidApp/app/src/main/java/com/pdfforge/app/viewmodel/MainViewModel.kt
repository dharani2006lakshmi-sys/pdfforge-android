package com.pdfforge.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfforge.app.updater.GithubRelease
import com.pdfforge.app.updater.UpdateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val updateManager: UpdateManager
) : ViewModel() {

    private val _updateAvailable = MutableStateFlow<GithubRelease?>(null)
    val updateAvailable: StateFlow<GithubRelease?> = _updateAvailable.asStateFlow()

    init {
        checkForUpdates()
    }

    private fun checkForUpdates() {
        viewModelScope.launch {
            val release = updateManager.checkForUpdate()
            if (release != null) {
                _updateAvailable.value = release
            }
        }
    }

    fun downloadUpdate(apkUrl: String, fileName: String) {
        updateManager.downloadAndInstallUpdate(apkUrl, fileName)
        _updateAvailable.value = null // Dismiss dialog
    }

    fun dismissUpdate() {
        _updateAvailable.value = null
    }
}
