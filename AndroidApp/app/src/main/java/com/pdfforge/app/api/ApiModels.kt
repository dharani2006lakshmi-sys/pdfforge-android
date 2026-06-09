package com.pdfforge.app.api

data class ApiResponse(
    val success: Boolean,
    val message: String?,
    val data: Any?
)

data class ProcessRequest(
    val toolId: String,
    val fileUri: String
)
