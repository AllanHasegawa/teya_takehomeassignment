package me.teyatha.albums.ui.albumdetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import me.teyatha.albums.domain.Album
import me.teyatha.albums.domain.interactors.GetAlbumById

@HiltViewModel(assistedFactory = AlbumDetailsViewModel.Factory::class)
internal class AlbumDetailsViewModel @AssistedInject constructor(
    @Assisted private val albumId: String,
    private val getAlbumById: GetAlbumById,
) : ViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(albumId: String): AlbumDetailsViewModel
    }

    sealed interface ViewState {
        data object Loading : ViewState
        data object Error : ViewState
        data class Content(val album: Album) : ViewState
    }

    val state = MutableStateFlow<ViewState>(ViewState.Loading)
    private val refreshSignal = Channel<Unit>(1)

    init {
        viewModelScope.launch {
            refreshSignal
                .receiveAsFlow()
                .onStart { emit(Unit) }
                .onEach { state.value = ViewState.Loading }
                .flatMapLatest { getAlbumById(albumId) }
                .mapLatest { result ->
                    result.getOrNull()?.let {
                        ViewState.Content(it)
                    } ?: run {
                        Log.d("Error", "Error", result.exceptionOrNull())
                        ViewState.Error
                    }
                }
                .collect { state.value = it }
        }
    }
}