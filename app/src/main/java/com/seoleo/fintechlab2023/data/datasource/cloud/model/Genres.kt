package com.seoleo.fintechlab2023.data.datasource.cloud.model

import com.google.gson.annotations.SerializedName


data class Genres(
    @SerializedName("genre") var genre: String? = null
)