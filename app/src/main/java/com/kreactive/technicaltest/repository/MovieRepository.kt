package com.kreactive.technicaltest.repository

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.jakewharton.rxrelay2.PublishRelay
import com.kreactive.technicaltest.api.OMDbService
import com.kreactive.technicaltest.api.NetworkStatus
import com.kreactive.technicaltest.factory.MovieDataSourceFactory
import com.kreactive.technicaltest.model.Movie
import com.kreactive.technicaltest.model.Type
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import rx.Observable
import rx.schedulers.Schedulers
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber

class MovieRepository(private val service: OMDbService) {

    val pagedListConfig: PagedList.Config
    private lateinit var sourceFactory : MovieDataSourceFactory

    val pagedListObservable : PublishRelay<PagedList<Movie>> = PublishRelay.create()
    private var pagedListDisposable : Disposable? = null

    val pagedListNetworkStatusObservable : PublishRelay<NetworkStatus> = PublishRelay.create()
    private var pagedListNetworkStatusDisposable : Disposable? = null

    init {
        pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(40)
                .setPageSize(20)
                .setPrefetchDistance(10)
                .build()
    }

    fun search(data : SearchDatas){
        pagedListDisposable?.dispose()
        pagedListNetworkStatusDisposable?.dispose()
        sourceFactory = MovieDataSourceFactory(service, data.search, data.type, data.year)

        val pagedList = RxPagedListBuilder(sourceFactory, pagedListConfig).buildObservable()
        val networkStatus = sourceFactory.sourceLiveData.flatMap { movieDataSource -> movieDataSource.networkStatus }

        pagedListDisposable = pagedList.subscribe(pagedListObservable)
        pagedListNetworkStatusDisposable = networkStatus.subscribe(pagedListNetworkStatusObservable)
    }

    fun getMovie(movie: Movie): Observable<NetworkStatus> {
        val obs = service.getMovie(movie.imdbID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    movie.rated = it.rated
                    movie.released = it.released
                    movie.runtime = it.runtime
                    movie.genre = it.genre
                    movie.director = it.director
                    movie.writer = it.writer
                    movie.actors = it.actors
                    movie.plot = it.plot
                    movie.language = it.language
                    movie.country = it.country
                    movie.awards = it.awards
                    movie.ratings = it.ratings
                    movie.metascore = it.metascore
                    movie.imdbRating = it.imdbRating
                    movie.imdbVotes = it.imdbVotes
                    movie.DVD = it.DVD
                    movie.boxOffice = it.boxOffice
                    movie.production = it.production
                    movie.website = it.website
                }
                .map<NetworkStatus> {
                    if (it.error == null) {
                        NetworkStatus.Success
                    } else {
                        NetworkStatus.Error(Throwable())
                    }
                }
                .onErrorReturn { NetworkStatus.Error(it) }
                .startWith(NetworkStatus.InProgress)
                .share()

        return obs
    }

    data class SearchDatas(val search: String, val type: Type?, val year: String?)
}