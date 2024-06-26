

package com.starry.myne.ui.screens.reader.activities

import android.content.ContentResolver
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import com.starry.myne.R
import com.starry.myne.ui.screens.reader.composables.ReaderContent
import com.starry.myne.ui.screens.reader.composables.ReaderScreen
import com.starry.myne.ui.screens.reader.composables.TransparentSystemBars
import com.starry.myne.ui.screens.reader.viewmodels.ReaderViewModel
import com.starry.myne.ui.screens.settings.viewmodels.SettingsViewModel
import com.starry.myne.ui.theme.MyneTheme
import com.starry.myne.utils.toToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.FileInputStream

object ReaderConstants {
    const val EXTRA_BOOK_ID = "reader_book_id"
    const val EXTRA_CHAPTER_IDX = "reader_chapter_index"
    const val DEFAULT_NONE = -100000

}

data class IntentData(
    val bookId: Int?, val chapterIndex: Int?, val isExternalBook: Boolean
)

@AndroidEntryPoint
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
class ReaderActivity : AppCompatActivity() {

    private lateinit var settingsViewModel: SettingsViewModel
    private val viewModel: ReaderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fullscreen mode that ignores any cutout, notch etc.
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.displayCutout())
        controller.hide(WindowInsetsCompat.Type.systemBars())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        // Set app theme.
        settingsViewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        // Set UI contents.
        setContent {
            MyneTheme(settingsViewModel = settingsViewModel) {

                val lazyListState = rememberLazyListState()
                val coroutineScope = rememberCoroutineScope()

                // Handle intent and load epub book.
                val intentData = handleIntent(intent = intent,
                    viewModel = viewModel,
                    contentResolver = contentResolver,
                    scrollToPosition = { index, offset ->
                        coroutineScope.launch {
                            lazyListState.scrollToItem(index, offset)
                        }
                    },
                    onError = {
                        getString(R.string.error).toToast(this)
                        finish()
                    })

                TransparentSystemBars(settingsViewModel = settingsViewModel)

                ReaderScreen(
                    viewModel = viewModel,
                    lazyListState = lazyListState,
                    readerContent = {
                        LaunchedEffect(lazyListState) {
                            snapshotFlow {
                                lazyListState.firstVisibleItemScrollOffset
                            }.collect { visibleChapterOffset ->
                                // fetch last visible chapter position and offset.
                                val visibleChapterIdx = lazyListState.firstVisibleItemIndex
                                // Set currently visible chapter & index.
                                viewModel.setVisibleChapterIndex(visibleChapterIdx)
                                viewModel.setChapterScrollPercent(
                                    calculateChapterPercentage(lazyListState)
                                )

                                // If book was not opened from external epub file, update the
                                // reading progress into the database.
                                if (!intentData.isExternalBook) {
                                    viewModel.updateReaderProgress(
                                        // Book ID is not null here since we are not opening
                                        // an external book.
                                        bookId = intentData.bookId!!,
                                        chapterIndex = visibleChapterIdx,
                                        chapterOffset = visibleChapterOffset
                                    )
                                }
                            }
                        }

                        // Reader content lazy column.
                        ReaderContent(viewModel = viewModel, lazyListState = lazyListState)
                    })
            }
        }
    }

}


/**
 * Handle intent and load epub book from given id or external file.
 *
 * @param intent Intent to handle.
 * @param viewModel ReaderViewModel to load book.
 * @param contentResolver ContentResolver to open input stream.
 * @param scrollToPosition Function to scroll to specific position.
 * @param onError Function to handle error.
 *
 * @return IntentData object containing book id, chapter index and isExternalBook.
 */
fun handleIntent(
    intent: Intent,
    viewModel: ReaderViewModel,
    contentResolver: ContentResolver,
    scrollToPosition: (index: Int, offset: Int) -> Unit,
    onError: () -> Unit
): IntentData {
    val bookId = intent.extras?.getInt(
        ReaderConstants.EXTRA_BOOK_ID, ReaderConstants.DEFAULT_NONE
    )
    val chapterIndex = intent.extras?.getInt(
        ReaderConstants.EXTRA_CHAPTER_IDX, ReaderConstants.DEFAULT_NONE
    )
    val isExternalBook = intent.type == "application/epub+zip"

    // Internal book
    if (bookId != null && bookId != ReaderConstants.DEFAULT_NONE) {
        // Load epub book from given id and set chapters as items in
        // reader's recycler view adapter.
        viewModel.loadEpubBook(bookId = bookId, onLoaded = {
            // if there is saved progress for this book, then scroll to
            // last page at exact position were used had left.
            if (it.readerData != null && chapterIndex == ReaderConstants.DEFAULT_NONE) {
                scrollToPosition(it.readerData.lastChapterIndex, it.readerData.lastChapterOffset)
            }
        })
        // if user clicked on specific chapter, then scroll to
        // that chapter directly.
        if (chapterIndex != null && chapterIndex != ReaderConstants.DEFAULT_NONE) {
            scrollToPosition(chapterIndex, 0)
        }

        // External book.
    } else if (isExternalBook) {
        intent.data?.let {
            contentResolver.openInputStream(it)?.let { ips ->
                viewModel.loadEpubBookExternal(ips as FileInputStream)
            }
        }
    } else {
        onError() // If no book id is provided, then show error.
    }

    return IntentData(bookId, chapterIndex, isExternalBook)
}

/**
 * Calculate the scroll percentage for the first visible item in a LazyColumn.
 *
 * @param lazyListState The LazyListState of the LazyColumn
 * @return The scroll percentage for the first visible item, or -1 if no item is visible
 */
fun calculateChapterPercentage(lazyListState: LazyListState): Float {
    val firstVisibleItem = lazyListState.layoutInfo.visibleItemsInfo.firstOrNull() ?: return -1f
    val listHeight =
        lazyListState.layoutInfo.viewportEndOffset - lazyListState.layoutInfo.viewportStartOffset

    // Calculate the scroll percentage for the first visible item
    val itemTop = firstVisibleItem.offset.toFloat()
    val itemBottom = itemTop + firstVisibleItem.size.toFloat()

    return if (itemTop >= listHeight || itemBottom <= 0f) {
        1f // Item is completely scrolled out of view
    } else {
        // Calculate the visible portion of the item
        val visiblePortion = if (itemTop < 0f) {
            itemBottom
        } else {
            listHeight - itemTop
        }
        // Calculate the scroll percentage based on the visible portion
        ((1f - visiblePortion / firstVisibleItem.size.toFloat())).coerceIn(0f, 1f)
    }
}

