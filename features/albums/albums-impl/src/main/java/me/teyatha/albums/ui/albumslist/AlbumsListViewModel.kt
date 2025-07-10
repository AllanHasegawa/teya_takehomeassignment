package me.teyatha.albums.ui.albumslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import me.teyatha.albums.domain.FeedList
import me.teyatha.albums.domain.interactors.GetAlbumsFeedList
import javax.inject.Inject

@HiltViewModel
internal class AlbumsListViewModel @Inject constructor(
    private val getAlbumsFeedList: GetAlbumsFeedList,
) : ViewModel() {
    sealed interface ViewState {
        data object Loading : ViewState
        data object Error : ViewState
        data class Content(
            val feed: FeedList,
        ) : ViewState
    }

    val state = MutableStateFlow<ViewState>(ViewState.Loading)
    private val refreshSignal = Channel<Unit>()

    init {
        viewModelScope.launch {
            refreshSignal.receiveAsFlow()
                .onStart { emit(Unit) }
                .onEach { state.value = ViewState.Loading }
                .flatMapConcat { getAlbumsFeedList() }
                .map { result ->
                    result.getOrNull()
                        ?.let { ViewState.Content(it) }
                        ?: run {
                            Log.d("Error", "Error", result.exceptionOrNull())
                            ViewState.Error
                        }
                }
                .collect { state.value = it }
        }
    }
}