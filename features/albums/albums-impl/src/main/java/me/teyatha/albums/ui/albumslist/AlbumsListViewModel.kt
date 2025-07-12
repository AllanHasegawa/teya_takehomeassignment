package me.teyatha.albums.ui.albumslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import me.teyatha.albums.domain.FeedList
import me.teyatha.albums.domain.interactors.GetAlbumsFeedList
import me.teyatha.core.DispatcherIo
import me.teyatha.core.LCE
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
internal class AlbumsListViewModel @Inject constructor(
    private val getAlbumsFeedList: GetAlbumsFeedList,
    @DispatcherIo dispatcher: CoroutineDispatcher,
) : ViewModel() {

    val state = MutableStateFlow<LCE<FeedList>>(LCE.Loading)
    private val refreshSignal = Channel<Unit>()

    init {
        viewModelScope.launch(dispatcher) {
            refreshSignal.receiveAsFlow()
                .onStart { emit(Unit) }
                .onEach { state.value = LCE.Loading }
                .flatMapConcat { getAlbumsFeedList() }
                .mapLatest {
                    // Artificial delay to test loading screen
                    delay(1.seconds)
                    it
                }
                .mapLatest { result ->
                    result.getOrNull()
                        ?.let { LCE.Content(it) }
                        ?: run {
                            Log.d("Error", "Error", result.exceptionOrNull())
                            LCE.Error
                        }
                }
                .collect { state.value = it }
        }
    }

    fun onErrorRetry() {
        refreshSignal.trySend(Unit)
    }
}