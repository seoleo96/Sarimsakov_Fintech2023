package com.seoleo.fintechlab2023

import android.app.Application
import com.seoleo.fintechlab2023.data.datasource.cache.FilmDao
import com.seoleo.fintechlab2023.data.datasource.cache.FilmsDatabase
import com.seoleo.fintechlab2023.data.datasource.cloud.CloudDataSource
import com.seoleo.fintechlab2023.data.datasource.cloud.CloudDataSourceImpl
import com.seoleo.fintechlab2023.data.datasource.cloud.FilmService
import com.seoleo.fintechlab2023.data.datasource.cloud.RetrofitInstance
import com.seoleo.fintechlab2023.data.repository.FilmRepository
import com.seoleo.fintechlab2023.data.repository.FilmRepositoryImpl
import com.seoleo.fintechlab2023.ui.screens.favourites.FavouritesViewModel
import com.seoleo.fintechlab2023.ui.screens.info.FilmInfoViewModel
import com.seoleo.fintechlab2023.ui.screens.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoinMain()
    }

    private val appModule = module {

        single<FilmService> {
            val retrofitInstance = RetrofitInstance()
            retrofitInstance.service(retrofitInstance.instance())
        }
        singleOf(::CloudDataSourceImpl) bind CloudDataSource::class
        single<FilmDao> {
            FilmsDatabase.getDatabase(androidContext()).filmDao()
        }
        singleOf(::FilmRepositoryImpl) bind FilmRepository::class
        viewModelOf(::MainViewModel)
        viewModelOf(::FavouritesViewModel)
        viewModelOf(::FilmInfoViewModel)
    }

    private fun startKoinMain() {
        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(applicationContext)
            modules(appModule)
        }
    }
}