package com.android.giphy.ui.home

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.giphy.R
import com.android.giphy.common.Event
import com.android.giphy.common.Result
import com.android.giphy.data.GifPagingSource
import com.android.giphy.data.GifRepository
import com.android.giphy.data.networking.GiphyService
import com.android.giphy.data.networking.model.Data
import com.android.giphy.model.Gif
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: GifRepository,
) : ViewModel() {

    private val _clickEvent = MutableLiveData<Event<Gif>>()
    val clickEvent: LiveData<Event<Gif>> = _clickEvent

    private val _snackbarText = MutableSharedFlow<Int>()
    val snackbarMessage: SharedFlow<Int> = _snackbarText

    val gifs = repository.gifFlow.asLiveData()

    val gifPaging: Flow<PagingData<Data>> {
        ret
    }

    init {
        viewModelScope.launch {
            val result = repository.getGifs()
            if (result is Result.Error) {
                _snackbarText.emit(R.string.error_message)
            }
        }
    }

    fun gifClicked(gif: Gif) {
        _clickEvent.value = Event(gif)
    }
}