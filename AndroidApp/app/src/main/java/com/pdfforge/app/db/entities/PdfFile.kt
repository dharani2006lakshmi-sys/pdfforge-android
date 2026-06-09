package com.pdfforge.app.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "pdf_files")
data class PdfFile(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val uri: String,
    val size: Long,
    val pageCount: Int,
    val addedAt: Long = System.currentTimeMillis()
)
