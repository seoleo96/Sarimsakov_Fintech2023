package com.seoleo.fintechlab2023.data.datasource.cloud.model

import com.google.gson.annotations.SerializedName
import com.seoleo.fintechlab2023.data.datasource.cache.FilmCacheModel


data class FilmCloudModel(
    @SerializedName("pagesCount") var pagesCount: Int? = null,
    @SerializedName("films") var films: List<Films> = arrayListOf()
) {

    fun getCacheList(): List<FilmCacheModel> {
        return films.map {
            it.toCacheModel()
        }
    }
}