package com.seoleo.fintechlab2023.data.datasource.cache

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface FilmDao {


    @Query("SELECT * FROM filmEntity")
    suspend fun getAll(): List<FilmCacheModel>

    @Query("SELECT * FROM filmEntity WHERE name LIKE :searchQuery")
    fun getAllFlow(searchQuery: String): Flow<List<FilmCacheModel>>

    @Query("SELECT * FROM filmEntity WHERE isFav and name LIKE :query")
    fun getAllFavouritesFlow(query: String): Flow<List<FilmCacheModel>>

    @Insert
    suspend fun insert(film: FilmCacheModel)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(films: List<FilmCacheModel>)

    @Update
    suspend fun update(film: FilmCacheModel): Int

    @Delete
    suspend fun delete(film: FilmCacheModel): Int

    @Query("DELETE FROM filmEntity")
    suspend fun deleteAll()
}