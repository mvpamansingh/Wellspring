

package com.starry.myne.ui.screens.detail.viewmodels

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.annotation.ExperimentalCoilApi
import com.starry.myne.database.library.LibraryDao
import com.starry.myne.database.library.LibraryItem
import com.starry.myne.repo.BookRepository
import com.starry.myne.repo.models.Book
import com.starry.myne.repo.models.BookSet
import com.starry.myne.repo.models.ExtraInfo
import com.starry.myne.utils.PreferenceUtil
import com.starry.myne.utils.book.BookDownloader
import com.starry.myne.utils.book.BookUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BookDetailScreenState(
    val isLoading: Boolean = true,
    val bookSet: BookSet = BookSet(0, null, null, emptyList()),
    val extraInfo: ExtraInfo = ExtraInfo(),
    val bookLibraryItem: LibraryItem? = null,
    val error: String? = null
)

@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    val libraryDao: LibraryDao,
    val bookDownloader: BookDownloader,
    private val preferenceUtil: PreferenceUtil
) : ViewModel() {
    var state by mutableStateOf(BookDetailScreenState())

    fun getInternalReaderSetting() = preferenceUtil.getBoolean(
        PreferenceUtil.INTERNAL_READER_BOOL, true
    )

    fun getBookDetails(bookId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // Reset Screen state.
            state = BookDetailScreenState()
            try {
                val bookSet = bookRepository.getBookById(bookId).getOrNull()!!
                val extraInfo = bookRepository.getExtraInfo(bookSet.books.first().title)
                state = if (extraInfo != null) {
                    state.copy(bookSet = bookSet, extraInfo = extraInfo)
                } else {
                    state.copy(bookSet = bookSet)
                }
                state = state.copy(
                    bookLibraryItem = libraryDao.getItemById(bookId.toInt()), isLoading = false
                )
            } catch (exc: Exception) {
                state =
                    state.copy(error = exc.localizedMessage ?: "unknown-error", isLoading = false)
            }
        }
    }

    fun downloadBook(
        book: Book, downloadProgressListener: (Float, Int) -> Unit
    ) {
        bookDownloader.downloadBook(book = book,
            downloadProgressListener = downloadProgressListener,
            onDownloadSuccess = { filePath ->
                insertIntoDB(book = book, filePath = filePath)
                state = state.copy(bookLibraryItem = libraryDao.getItemById(book.id))
            }
        )
    }

    private fun insertIntoDB(book: Book, filePath: String) {
        val libraryItem = LibraryItem(
            bookId = book.id,
            title = book.title,
            authors = BookUtils.getAuthorsAsString(book.authors),
            filePath = filePath,
            createdAt = System.currentTimeMillis()
        )
        libraryDao.insert(libraryItem)
    }
}