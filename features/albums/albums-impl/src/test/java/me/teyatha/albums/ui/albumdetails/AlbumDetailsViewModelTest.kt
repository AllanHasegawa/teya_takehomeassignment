package me.teyatha.albums.ui.albumdetails

import android.util.Log
import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import me.teyatha.albums.domain.Album
import me.teyatha.albums.domain.interactors.GetAlbumById
import me.teyatha.core.LCE
import me.teyatha.core.Randomiser
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

internal class AlbumDetailsViewModelTest {
    // If reused, this could be shared with Mother pattern
    private val album = Album(
        id = "albumId",
        name = "album name",
        images = listOf(
            Album.Image(
                url = "image url",
                heightPx = 42,
            )
        ),
        priceFormatted = "$42",
        link = "album url",
        artist = "album artist",
        releaseDateFormatted = "release date",
    )

    private val getAlbumById: GetAlbumById = mockk()
    private val randomiser: Randomiser = mockk()
    private val dispatcher = StandardTestDispatcher()

    private fun undertest() =
        AlbumDetailsViewModel(
            albumId = album.id,
            getAlbumById = getAlbumById,
            randomiser = randomiser,
            dispatcher = dispatcher,
        )

    @Before
    fun setup() {
        mockkStatic(Log::class)
        every { Log.d(any(), any(), any()) } returns 0
        every { randomiser.weightedBoolean(any()) } returns true
    }

    @Test
    fun `When album not found, emit error state`() = runTest {
        every { getAlbumById(eq(album.id)) } returns flowOf(Result.success(null))

        val viewModel = undertest()
        viewModel.state.test {
            assertEquals(LCE.Loading, awaitItem())
            dispatcher.scheduler.advanceUntilIdle()
            assertEquals(LCE.Error, awaitItem())
        }
    }

    @Test
    fun `When network error, emit error`() = runTest {
        every { getAlbumById(eq(album.id)) } returns flowOf(Result.failure(RuntimeException()))

        val viewModel = undertest()
        viewModel.state.test {
            assertEquals(LCE.Loading, awaitItem())
            dispatcher.scheduler.advanceUntilIdle()
            assertEquals(LCE.Error, awaitItem())
        }
    }

    @Test
    fun `When album found, emit content`() = runTest {
        every { getAlbumById(eq(album.id)) } returns flowOf(Result.success(album))

        val viewModel = undertest()
        viewModel.state.test {
            assertEquals(LCE.Loading, awaitItem())
            dispatcher.scheduler.advanceUntilIdle()
            assertEquals(LCE.Content(album), awaitItem())
        }
    }

    @Test
    fun `When retry is requested, refetch album`() = runTest {
        every { getAlbumById(eq(album.id)) }
            .returns(flowOf(Result.failure(RuntimeException())))
            .andThen(flowOf(Result.success(album)))

        val viewModel = undertest()
        viewModel.state.test {
            assertEquals(LCE.Loading, awaitItem())
            dispatcher.scheduler.advanceUntilIdle()
            assertEquals(LCE.Error, awaitItem())

            viewModel.onErrorRetry()
            dispatcher.scheduler.advanceUntilIdle()
            assertEquals(LCE.Loading, awaitItem())
            dispatcher.scheduler.advanceUntilIdle()
            assertEquals(LCE.Content(album), awaitItem())
        }

        @Test
        fun `When randomiser returns false, emit error`() = runTest {
            every { getAlbumById(eq(album.id)) }
                .returns(flowOf(Result.success(album)))
            every { randomiser.weightedBoolean(any()) } returns false

            val viewModel = undertest()
            viewModel.state.test {
                assertEquals(LCE.Loading, awaitItem())
                dispatcher.scheduler.advanceUntilIdle()
                assertEquals(LCE.Error, awaitItem())
            }
        }
    }
}