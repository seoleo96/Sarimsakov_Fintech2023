package com.seoleo.fintechlab2023.data.datasource.cloud

import android.util.Log
import com.google.gson.Gson
import com.seoleo.fintechlab2023.data.FilmsInfoResult
import com.seoleo.fintechlab2023.data.FilmsResult
import com.seoleo.fintechlab2023.data.datasource.cloud.model.FilmCloudModel
import com.seoleo.fintechlab2023.data.datasource.cloud.model.FilmInfoModel

interface CloudDataSource {
    suspend fun fetchFilms(): FilmsResult
    suspend fun fetchFilmInfo(id: Int): FilmsInfoResult
}

class CloudDataSourceImpl(
    private val service: FilmService
) : CloudDataSource {
    override suspend fun fetchFilms(): FilmsResult {
        return try {
            val response = service.fetchFilms()
            if (response.code() == 200) {
                val data = Gson().toJson(response.body())
                val filmCloudModel = Gson().fromJson(data, FilmCloudModel::class.java)
                FilmsResult.Success(filmCloudModel)
            } else {
                FilmsResult.Fail(response.code())
            }
        } catch (e: Exception) {
            FilmsResult.Fail(null)
        }
    }

    override suspend fun fetchFilmInfo(id: Int): FilmsInfoResult {
        return try {
            val response = service.fetchFilmInfo(id)
            if (response.code() == 200) {
                Log.e("TAG", "response.body(): ${response.body()}")
                val data = Gson().toJson(response.body())
                val filmCloudModel = Gson().fromJson(data, FilmInfoModel::class.java)
                FilmsInfoResult.Success(filmCloudModel.toModel())
            } else {
                FilmsInfoResult.Fail(response.code())
            }
        } catch (e: Exception) {
            FilmsInfoResult.Fail(null)
        }
    }
}