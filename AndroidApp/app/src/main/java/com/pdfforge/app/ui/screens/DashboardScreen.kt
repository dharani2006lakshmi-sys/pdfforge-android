package com.pdfforge.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pdfforge.app.ui.theme.*

data class PdfTool(val id: String, val name: String, val category: String, val type: ToolType)
enum class ToolType { OFFLINE, ONLINE }

fun getPdfTools() = listOf(
    PdfTool("merge", "Merge PDF", "Organize", ToolType.OFFLINE),
    PdfTool("split", "Split PDF", "Organize", ToolType.OFFLINE),
    PdfTool("compress", "Compress PDF", "Optimize", ToolType.OFFLINE),
    PdfTool("pdf2word", "PDF to Word", "Convert", ToolType.ONLINE),
    PdfTool("protect", "Protect PDF", "Security", ToolType.OFFLINE)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(onToolClick: (PdfTool) -> Unit) {
    val tools = remember { getPdfTools() }
    val categories = listOf("All", "Organize", "Optimize", "Security", "Convert")
    var selectedCat by remember { mutableStateOf("All") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PDFforge", color = TextDark) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundDark)
            )
        },
        containerColor = BackgroundDark
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { cat ->
                    FilterChip(
                        selected = cat == selectedCat,
                        onClick = { selectedCat = cat },
                        label = { Text(cat) },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = SurfaceDark,
                            labelColor = MutedDark,
                            selectedContainerColor = Purple.copy(alpha = 0.2f),
                            selectedLabelColor = Purple
                        )
                    )
                }
            }
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val filteredTools = tools.filter { selectedCat == "All" || it.category == selectedCat }
                items(filteredTools) { tool ->
                    ToolCard(tool = tool, onClick = { onToolClick(tool) })
                }
            }
        }
    }
}

@Composable
fun ToolCard(tool: PdfTool, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = CardDark),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val dotColor = when (tool.category) {
                "Organize" -> Purple
                "Optimize" -> Green
                "Convert" -> Cyan
                "Security" -> Red
                else -> Color.Gray
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(8.dp).background(dotColor, RoundedCornerShape(4.dp)))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = tool.name, color = TextDark, style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))
            val badgeColor = if (tool.type == ToolType.OFFLINE) Green else Purple
            val badgeText = if (tool.type == ToolType.OFFLINE) "Offline" else "Online"
            
            Surface(
                color = badgeColor.copy(alpha = 0.15f),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = badgeText,
                    color = badgeColor,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}
