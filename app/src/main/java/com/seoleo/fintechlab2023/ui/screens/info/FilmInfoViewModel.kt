package com.seoleo.fintechlab2023.ui.screens.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoleo.fintechlab2023.data.FilmsInfoResult
import com.seoleo.fintechlab2023.data.repository.FilmRepository
import com.seoleo.fintechlab2023.ui.helpers.SingleLiveEvent
import com.seoleo.fintechlab2023.ui.model.FilmInfoUIModel
import com.seoleo.fintechlab2023.ui.model.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilmInfoViewModel(
    private val repository: FilmRepository,
) : ViewModel() {

    private val _message = SingleLiveEvent<Message>()
    private val _progress = SingleLiveEvent<Boolean>()
    private val _content = MutableLiveData<FilmInfoUIModel>()
    val content: LiveData<FilmInfoUIModel> = _content

    fun message() = _message
    fun progress() = _progress

    fun getInfoFilm(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _progress.postValue(true)
            val result = repository.fetchFilmInfo(id)
            _progress.postValue(false)
            when (result) {
                is FilmsInfoResult.Fail -> {
                    val messageContent = when (result.responseCode) {
                        null -> Message.NoInternet
                        401 -> Message.TokenNotFound
                        402 -> Message.LargeRequestLimit
                        429 -> Message.ManyRequests
                        else -> {
                            throw IllegalStateException("something went wrong")
                        }
                    }
                    _message.postValue(messageContent)
                }
                is FilmsInfoResult.Success -> {
                    _content.postValue(result.filmInfoUIModel)
                }
            }
        }
    }
}