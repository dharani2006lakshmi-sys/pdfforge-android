package com.pdfforge.app.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tool_history")
data class ToolHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val toolId: String,        // "merge", "split", etc
    val toolName: String,
    val inputFileName: String,
    val outputUri: String,
    val fileSize: Long,
    val timestamp: Long = System.currentTimeMillis(),
    val success: Boolean = true
)
