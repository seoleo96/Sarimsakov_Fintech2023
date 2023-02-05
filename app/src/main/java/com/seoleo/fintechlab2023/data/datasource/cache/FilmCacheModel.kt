package com.seoleo.fintechlab2023.data.datasource.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seoleo.fintechlab2023.ui.model.FilmUiModel

@Entity(tableName = "filmEntity")
data class FilmCacheModel(
    @PrimaryKey
    val filmId: Int,
    val name: String,
    val genre: String,
    val year: String,
    val posterUrl: String,
    val isFav: Boolean = false
) {

    fun toUiModel(): FilmUiModel {
        return FilmUiModel(
            filmId, name, genre.replaceFirstChar { it.uppercase() }, year, posterUrl, isFav
        )
    }
}