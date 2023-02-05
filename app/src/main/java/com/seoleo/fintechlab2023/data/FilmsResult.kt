package com.seoleo.fintechlab2023.data

import com.seoleo.fintechlab2023.data.datasource.cloud.model.FilmCloudModel
import com.seoleo.fintechlab2023.ui.model.FilmInfoUIModel


sealed class FilmsResult {
    class Success(val filmCloudModel: FilmCloudModel) : FilmsResult()
    class Fail(val responseCode: Int?) : FilmsResult()
}

sealed class FilmsInfoResult {
    class Success(val filmInfoUIModel: FilmInfoUIModel) : FilmsInfoResult()
    class Fail(val responseCode: Int?) : FilmsInfoResult()
}