


package com.example.wellspring.database.reader

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reader_table")
data class ReaderData(
    @ColumnInfo(name = "book_id") val bookId: Int,
    @ColumnInfo(name = "last_chapter_index") val lastChapterIndex: Int,
    @ColumnInfo(name = "last_chapter_offset") val lastChapterOffset: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun getProgressPercent(totalChapters: Int) =
        String.format("%.2f", ((lastChapterIndex + 1).toFloat() / totalChapters.toFloat()) * 100f)
}