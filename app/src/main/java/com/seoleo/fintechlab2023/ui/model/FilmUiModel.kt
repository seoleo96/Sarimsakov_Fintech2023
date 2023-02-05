package com.seoleo.fintechlab2023.ui.model

import com.seoleo.fintechlab2023.data.datasource.cache.FilmCacheModel

data class FilmUiModel(
    val filmId: Int,
    val name: String,
    val genre: String,
    val year: String,
    val posterUrl: String,
    val isFav: Boolean = false
) {

    fun toCacheModel() = FilmCacheModel(
        filmId, name, genre, year, posterUrl, !isFav
    )
}