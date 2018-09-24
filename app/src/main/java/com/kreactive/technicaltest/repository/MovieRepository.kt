package com.kreactive.technicaltest.repository

import com.jakewharton.rxrelay2.BehaviorRelay
import com.kreactive.technicaltest.api.OMDbService
import com.kreactive.technicaltest.api.NetworkStatus
import com.kreactive.technicaltest.model.Movie
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
}