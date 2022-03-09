package com.android.giphy.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.giphy.R
import com.android.giphy.common.Result
import com.android.giphy.data.DetailGifRepository
import com.android.giphy.model.Gif
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val repository: DetailGifRepository
) : ViewModel() {

    private val _gif = MutableLiveData<Gif>()
    val gif : LiveData<Gif> = _gif

    private val _snackbarText = MutableSharedFlow<Int>()
    val snackbarMessage: SharedFlow<Int> = _snackbarText

    fun getGif(id: String) {
        viewModelScope.launch {
            val result = repository.viewGif(id)
            if (result is Result.Success) {
                _gif.value = result.data
            } else {
                _snackbarText.emit(R.string.error_message)
            }
        }
    }

}