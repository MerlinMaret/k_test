package com.kreactive.technicaltest.repository

import com.jakewharton.rxrelay2.BehaviorRelay
import com.kreactive.technicaltest.api.OMDbService
import com.kreactive.technicaltest.api.NetworkStatus
import com.kreactive.technicaltest.model.Movie
import rx.Observable
import rx.schedulers.Schedulers
import rx.android.schedulers.AndroidSchedulers

class MovieRepository(private val service : OMDbService){

    val movies : BehaviorRelay<List<Movie>> = BehaviorRelay.createDefault(emptyList())

    fun search(search : String): Observable<NetworkStatus> {

        val obs = service.search(search)
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