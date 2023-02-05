package com.seoleo.fintechlab2023.data.datasource.cloud.model

import com.google.gson.annotations.SerializedName
import com.seoleo.fintechlab2023.data.datasource.cache.FilmCacheModel


data class Films(
    @SerializedName("filmId") var filmId: Int? = null,
    @SerializedName("nameRu") var nameRu: String? = null,
    @SerializedName("nameEn") var nameEn: String? = null,
    @SerializedName("year") var year: String? = null,
    @SerializedName("filmLength") var filmLength: String? = null,
    @SerializedName("countries") var countries: ArrayList<Countries> = arrayListOf(),
    @SerializedName("genres") var genres: ArrayList<Genres> = arrayListOf(),
    @SerializedName("rating") var rating: String? = null,
    @SerializedName("ratingVoteCount") var ratingVoteCount: Int? = null,
    @SerializedName("posterUrl") var posterUrl: String? = null,
    @SerializedName("posterUrlPreview") var posterUrlPreview: String? = null,
    @SerializedName("ratingChange") var ratingChange: String? = null
) {

    fun toCacheModel() = FilmCacheModel(
        filmId = this.filmId ?: 0,
        name = this.nameRu ?: "",
        genre = if (genres.isNotEmpty()) genres[0].genre ?: "" else "",
        year = year ?: "",
        posterUrl = posterUrl ?: "",
    )
}