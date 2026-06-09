package com.pdfforge.app.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfforge.app.repository.PdfRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ToolUiState(
    val isLoading: Boolean = false,
    val result: ByteArray? = null,
    val error: String? = null
)

@HiltViewModel
class ToolViewModel @Inject constructor(
    private val repository: PdfRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ToolUiState())
    val uiState: StateFlow<ToolUiState> = _uiState.asStateFlow()

    fun processTool(tool: String, fileUri: Uri) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, result = null) }
            val result = repository.process(tool, fileUri)
            if (result.isSuccess) {
                _uiState.update { it.copy(isLoading = false, result = result.getOrNull()) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = result.exceptionOrNull()?.message ?: "Unknown error") }
            }
        }
    }

    fun mergePdfs(uris: List<Uri>) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, result = null) }
            val result = repository.mergePdfs(uris)
            if (result.isSuccess) {
                _uiState.update { it.copy(isLoading = false, result = result.getOrNull()) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = result.exceptionOrNull()?.message ?: "Unknown error") }
            }
        }
    }

    fun resetState() {
        _uiState.value = ToolUiState()
    }
}
