package me.teyatha.albums.ui.albumdetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import me.teyatha.albums.domain.Album
import me.teyatha.albums.domain.interactors.GetAlbumById
import me.teyatha.core.Delayer
import me.teyatha.core.DispatcherIo
import me.teyatha.core.LCE
import me.teyatha.core.Randomiser

@HiltViewModel(assistedFactory = AlbumDetailsViewModel.Factory::class)
internal class AlbumDetailsViewModel @AssistedInject constructor(
    @Assisted private val albumId: String,
    private val getAlbumById: GetAlbumById,
    private val randomiser: Randomiser,
    private val delayer: Delayer,
    @DispatcherIo dispatcher: CoroutineDispatcher,
) : ViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(albumId: String): AlbumDetailsViewModel
    }

    val state = MutableStateFlow<LCE<Album>>(LCE.Loading)
    private val refreshSignal = Channel<Unit>(1)

    init {
        viewModelScope.launch(dispatcher) {
            refreshSignal
                .receiveAsFlow()
                .onStart { emit(Unit) }
                .onEach { state.value = LCE.Loading }
                .flatMapLatest { getAlbumById(albumId) }
                .mapLatest {
                    // Fake processing time and error to test out error screen
                    delayer.delayForABit()
                    when (randomiser.weightedBoolean(chanceForTrue = 0.5f)) {
                        true -> it
                        false -> Result.success(null)
                    }
                }
                .mapLatest { result ->
                    result.getOrNull()?.let {
                        LCE.Content(it)
                    } ?: run {
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