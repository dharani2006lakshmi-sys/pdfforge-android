package com.pdfforge.app.pdf

import android.content.Context
import android.net.Uri
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.utils.PdfMerger
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class LocalPdfEngine @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun mergePdfs(uris: List<Uri>): Result<ByteArray> = withContext(Dispatchers.IO) {
        try {
            val output = ByteArrayOutputStream()
            val writer = PdfWriter(output)
            val pdfDoc = PdfDocument(writer)
            val merger = PdfMerger(pdfDoc)
            
            uris.forEach { uri ->
                context.contentResolver.openInputStream(uri)?.use { stream ->
                    val src = PdfDocument(PdfReader(stream))
                    merger.merge(src, 1, src.numberOfPages)
                    src.close()
                }
            }
            
            pdfDoc.close()
            Result.success(output.toByteArray())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun process(tool: String, fileUri: Uri): Result<ByteArray> = withContext(Dispatchers.IO) {
        // Implement other single-file tools here (Split, Rotate, Compress, etc.)
        Result.failure(Exception("Not implemented yet"))
    }
}
