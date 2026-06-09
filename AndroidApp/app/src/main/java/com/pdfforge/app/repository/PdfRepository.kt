package com.pdfforge.app.repository

import android.net.Uri
import com.pdfforge.app.api.PdfForgeService
import com.pdfforge.app.db.PdfDao
import com.pdfforge.app.pdf.LocalPdfEngine
import javax.inject.Inject

class PdfRepository @Inject constructor(
    private val localEngine: LocalPdfEngine,
    private val remoteApi: PdfForgeService,
    private val dao: PdfDao
) {
    suspend fun process(tool: String, fileUri: Uri): Result<ByteArray> {
        return when (tool) {
            "merge", "split", "rotate", "compress",
            "protect", "unlock", "watermark",
            "grayscale", "pagenumbers", "flatten" ->
                localEngine.process(tool, fileUri)

            "pdf2word", "word2pdf", "pdf2excel",
            "pdf2ppt", "html2pdf", "ocr", "repair" -> {
                Result.failure(Exception("Remote tools not fully implemented in this demo phase"))
            }

            else -> Result.failure(Exception("Unknown tool"))
        }
    }

    suspend fun mergePdfs(uris: List<Uri>): Result<ByteArray> {
        return localEngine.mergePdfs(uris)
    }
}
