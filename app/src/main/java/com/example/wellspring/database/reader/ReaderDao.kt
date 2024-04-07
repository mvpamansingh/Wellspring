

package com.example.wellspring.database.reader

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ReaderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(readerData: ReaderData)

    @Query("DELETE FROM reader_table WHERE book_id = :bookId")
    fun delete(bookId: Int)

    @Query(
        "UPDATE reader_table SET "
                + "last_chapter_index = :lastChapterIndex,"
                + "last_chapter_offset = :lastChapterOffset"
                + " WHERE  book_id = :bookId"
    )
    fun update(bookId: Int, lastChapterIndex: Int, lastChapterOffset: Int)

    @Query("SELECT * FROM reader_table WHERE book_id = :bookId")
    fun getReaderData(bookId: Int): ReaderData?

    @Query("SELECT * FROM reader_table WHERE book_id = :bookId")
    fun getReaderDataAsFlow(bookId: Int): Flow<ReaderData?>
}