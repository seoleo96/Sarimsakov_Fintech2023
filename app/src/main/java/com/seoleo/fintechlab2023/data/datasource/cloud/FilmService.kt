package com.seoleo.fintechlab2023.data.datasource.cloud

import retrofit2.Response
import retrofit2.http.*

interface FilmService {

    @GET("api/v2.2/films/top")
    suspend fun fetchFilms(
        @Query("type") type: String = "TOP_100_POPULAR_FILMS"
    ): Response<Any>

    @GET("api/v2.2/films/{id}/")
    suspend fun fetchFilmInfo(
        @Path("id") id: Int
    ): Response<Any>
}