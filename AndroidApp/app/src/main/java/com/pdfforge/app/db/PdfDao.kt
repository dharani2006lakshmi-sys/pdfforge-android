package com.pdfforge.app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pdfforge.app.db.entities.ToolHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface PdfDao {
    @Query("SELECT * FROM tool_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<ToolHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: ToolHistory)

    @Query("DELETE FROM tool_history WHERE id = :id")
    suspend fun deleteHistory(id: Int)

    @Query("SELECT COUNT(*) FROM tool_history WHERE toolId = :toolId")
    fun getToolUsageCount(toolId: String): Flow<Int>
}
