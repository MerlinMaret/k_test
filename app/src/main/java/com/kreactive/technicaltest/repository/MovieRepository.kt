package com.kreactive.technicaltest.repository

import com.jakewharton.rxrelay2.BehaviorRelay
import com.kreactive.technicaltest.api.OMDbService
import com.kreactive.technicaltest.api.NetworkStatus
import com.kreactive.technicaltest.model.Movie
import com.kreactive.technicaltest.model.Rating
import com.kreactive.technicaltest.model.Type
import rx.Observable
import rx.schedulers.Schedulers
import rx.android.schedulers.AndroidSchedulers
import java.time.Year

class MovieRepository(private val service : OMDbService){

    val movies : BehaviorRelay<List<Movie>> = BehaviorRelay.createDefault(emptyList())

    fun search(search : String, type : Type?, year: String?): Observable<NetworkStatus> {

        val obs = service.search(search, type = type.toString(), year = year)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    if(it.searchResult != null){
                        movies.accept(it.searchResult)
                    }
                }
                .map<NetworkStatus> {
                    if(it.getError() == null){
                        NetworkStatus.Success
                    }
                    else {
                        it.getError()?.let { error -> NetworkStatus.Error(error)  }
                    }
                }
                .onErrorReturn { NetworkStatus.Error(it) }
                .startWith(NetworkStatus.InProgress)
                .share()

        return obs
    }

    fun getMovie(movie : Movie): Observable<NetworkStatus>{
        val obs = service.getMovie(movie.imdbID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    movie.rated = it.rated
                    movie.released= it.released
                    movie.runtime= it.runtime
                    movie.genre= it.genre
                    movie.director= it.director
                    movie.writer= it.writer
                    movie.actors= it.actors
                    movie.plot= it.plot
                    movie.language= it.language
                    movie.country= it.country
                    movie.awards= it.awards
                    movie.ratings= it.ratings
                    movie.metascore= it.metascore
                    movie.imdbRating= it.imdbRating
                    movie.imdbVotes= it.imdbVotes
                    movie.DVD= it.DVD
                    movie.boxOffice= it.boxOffice
                    movie.production= it.production
                    movie.website= it.website
                    movies.accept(movies.value)
                }
                .map<NetworkStatus> {
                    if(it.error == null){
                        NetworkStatus.Success
                    }
                    else {
                        NetworkStatus.Error(Throwable())
                    }
                }
                .onErrorReturn { NetworkStatus.Error(it) }
                .startWith(NetworkStatus.InProgress)
                .share()

        return obs
    }
}