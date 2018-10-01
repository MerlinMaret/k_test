package com.kreactive.technicaltest.datasource

import androidx.paging.PageKeyedDataSource
import com.jakewharton.rxrelay2.BehaviorRelay
import com.kreactive.technicaltest.api.NetworkStatus
import com.kreactive.technicaltest.api.OMDbService
import com.kreactive.technicaltest.api.result.SearchResult
import com.kreactive.technicaltest.model.Movie
import com.kreactive.technicaltest.model.Type
import com.kreactive.technicaltest.repository.MovieRepository
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MovieDataSource(private val service: OMDbService,
                      val search: String,
                      val type: Type?,
                      val year: String?) : PageKeyedDataSource<Int, Movie>() {

    val networkStatus: BehaviorRelay<NetworkStatus> = BehaviorRelay.createDefault(NetworkStatus.InProgress)

    val initPage = 1

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        search(
                initPage,
                {
                    callback.onResult(it, null, initPage + 1)
                })
                .map<NetworkStatus> {
                    if (it.getError() == null) {
                        NetworkStatus.Success
                    } else {
                        it.getError()?.let { error -> NetworkStatus.Error(error) }
                    }
                }
                .onErrorReturn { NetworkStatus.Error(it) }
                .startWith(NetworkStatus.InProgress)
                .share()
                .subscribe(
                        {
                            //networkStatus.accept(it)
                        },
                        {
                            //networkStatus.accept(NetworkStatus.Error(it))
                        }

                )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val page = params.key
        search(
                page
        ) {
            callback.onResult(it, page + 1)
        }.subscribe()
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        //Nothing
    }

    private fun search(page: Int, onNext: (result: List<Movie>) -> Unit): Observable<SearchResult> {

        return service.search(search, type = type.toString(), year = year, page = page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    val searchResult = it.searchResult
                    if (searchResult != null) {
                        onNext.invoke(searchResult)
                    }
                }

    }
}