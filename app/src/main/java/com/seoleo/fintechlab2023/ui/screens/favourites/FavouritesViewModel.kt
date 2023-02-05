package com.seoleo.fintechlab2023.ui.screens.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoleo.fintechlab2023.data.repository.FilmRepository
import com.seoleo.fintechlab2023.ui.helpers.SingleLiveEvent
import com.seoleo.fintechlab2023.ui.model.FilmUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val repository: FilmRepository,
) : ViewModel() {

    private val _list = MutableLiveData<List<FilmUiModel>>()
    val list: LiveData<List<FilmUiModel>> = _list
    private val _emptySearchResponse = SingleLiveEvent<Boolean>()

    init {
        fetchFavFilms()
    }

    fun emptySearchResponse() = _emptySearchResponse

    fun fetchFavFilms(query: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            repository.favouriteFilmsFlow("%$query%").collectLatest { cacheList ->
                val uiModel = cacheList.map { cacheModel ->
                    cacheModel.toUiModel()
                }
                _list.postValue(uiModel)
                _emptySearchResponse.postValue(uiModel.isEmpty())
            }
        }
    }

    fun toFavourite(filmUiModel: FilmUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFilm(filmUiModel.toCacheModel())
        }
    }
}