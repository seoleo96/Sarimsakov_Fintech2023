package com.seoleo.fintechlab2023.data.repository

import com.seoleo.fintechlab2023.data.FilmsInfoResult
import com.seoleo.fintechlab2023.data.FilmsResult
import com.seoleo.fintechlab2023.data.datasource.cache.FilmCacheModel
import com.seoleo.fintechlab2023.data.datasource.cache.FilmDao
import com.seoleo.fintechlab2023.data.datasource.cloud.CloudDataSource
import com.seoleo.fintechlab2023.data.datasource.cloud.model.FilmCloudModel
import kotlinx.coroutines.flow.Flow

interface FilmRepository {
    fun filmsFlow(query: String): Flow<List<FilmCacheModel>>
    fun favouriteFilmsFlow(query: String): Flow<List<FilmCacheModel>>
    suspend fun fetchFilms(): FilmsResult
    suspend fun fetchFilmInfo(id: Int): FilmsInfoResult
    suspend fun updateFilm(filmCacheModel: FilmCacheModel)
}

class FilmRepositoryImpl(
    private val filmDao: FilmDao,
    private val cloudDataSource: CloudDataSource,
) : FilmRepository {

    override fun filmsFlow(query: String) = filmDao.getAllFlow(query)

    override fun favouriteFilmsFlow(query: String) = filmDao.getAllFavouritesFlow(query)

    override suspend fun updateFilm(filmCacheModel: FilmCacheModel) {
        filmDao.update(filmCacheModel)
    }

    override suspend fun fetchFilms(): FilmsResult {
        val cache = filmDao.getAll()
        if (cache.isNotEmpty()) {
            return FilmsResult.Success(FilmCloudModel())
        }
        val result = cloudDataSource.fetchFilms()
        return if (result is FilmsResult.Success) {
            val cacheList = result.filmCloudModel.getCacheList()
            filmDao.insert(cacheList)
            FilmsResult.Success(result.filmCloudModel)
        } else {
            result
        }
    }

    override suspend fun fetchFilmInfo(id: Int) = cloudDataSource.fetchFilmInfo(id)
}