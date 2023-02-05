package com.seoleo.fintechlab2023.ui.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoleo.fintechlab2023.data.FilmsResult
import com.seoleo.fintechlab2023.data.repository.FilmRepository
import com.seoleo.fintechlab2023.ui.helpers.SingleLiveEvent
import com.seoleo.fintechlab2023.ui.model.FilmUiModel
import com.seoleo.fintechlab2023.ui.model.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: FilmRepository,
) : ViewModel() {

    private val _message = SingleLiveEvent<Message>()
    private val _progress = SingleLiveEvent<Boolean>()
    private val _emptySearchResponse = SingleLiveEvent<Boolean>()
    private val _list = MutableLiveData<List<FilmUiModel>>()
    val list: LiveData<List<FilmUiModel>> = _list

    init {
        fetchFilms()
        fetchFilmsFlow()
    }

    fun message() = _message

    fun progress() = _progress

    fun emptySearchResponse() = _emptySearchResponse

    fun fetchFilmsFlow(query: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            repository.filmsFlow("%$query%").collectLatest { cacheList ->
                val uiModel = cacheList.map { cacheModel ->
                    cacheModel.toUiModel()
                }
                _list.postValue(uiModel)
                _emptySearchResponse.postValue(uiModel.isEmpty())
            }
        }
    }

    fun fetchFilms() {
        viewModelScope.launch(Dispatchers.IO) {
            _progress.postValue(true)
            val result = repository.fetchFilms()
            _progress.postValue(false)
            when (result) {
                is FilmsResult.Success -> {
                    _message.postValue(Message.Success)
                }
                is FilmsResult.Fail -> {
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
            }
        }
    }

    fun toFavourite(filmUiModel: FilmUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFilm(filmUiModel.toCacheModel())
        }
    }
}