package com.pdfforge.app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pdfforge.app.db.entities.PdfFile
import com.pdfforge.app.db.entities.ToolHistory

@Database(entities = [PdfFile::class, ToolHistory::class], version = 1, exportSchema = false)
abstract class PdfDatabase : RoomDatabase() {
    abstract fun pdfDao(): PdfDao
}
