package com.kreactive.technicaltest.factory

import androidx.paging.DataSource
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.kreactive.technicaltest.api.OMDbService
import com.kreactive.technicaltest.datasource.MovieDataSource
import com.kreactive.technicaltest.model.Movie
import com.kreactive.technicaltest.model.Type

class MovieDataSourceFactory(private val service : OMDbService,
                             val search : String,
                             val type : Type?,
                             val year : String?) : DataSource.Factory<Int, Movie>() {

    val sourceLiveData : BehaviorRelay<MovieDataSource> = BehaviorRelay.create()

    override fun create(): DataSource<Int, Movie> {
        val source = MovieDataSource(service, search, type, year)
        sourceLiveData.accept(source)
        return source
    }
}