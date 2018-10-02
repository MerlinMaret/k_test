package com.kreactive.technicaltest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.text.TextUtils
import androidx.paging.PagedList
import com.jakewharton.rxrelay2.BehaviorRelay
import com.kreactive.technicaltest.api.NetworkStatus
import com.kreactive.technicaltest.manager.ErrorManager
import com.kreactive.technicaltest.model.Movie
import com.kreactive.technicaltest.model.Type
import com.kreactive.technicaltest.repository.MovieRepository
import com.kreactive.technicaltest.utils.disposedBy
import com.kreactive.technicaltest.viewmodel.base.BaseViewModel
import io.reactivex.Observable
import rx.Subscription
import timber.log.Timber

class SearchFragmentViewModel(private val movieRepository: MovieRepository, private val errorManager: ErrorManager) : BaseViewModel() {

    val searchTextRelay: BehaviorRelay<String> = movieRepository.searchTextRelay
    val movies: Observable<PagedList<Movie>>
    val searchingStatus: Observable<NetworkStatus>

    //region Init

    init {
        initSearch()
        movies = initMovies()
        searchingStatus = initSearchingStatus()
    }

    private fun initSearch() {
        searchTextRelay.flatMap { text ->
            val searchData = MovieRepository.SearchDatas(text, null, null)
            movieRepository.search(searchData)
            movieRepository.listingObservable.flatMap { listing -> listing.pagedList }
        }
                .subscribe()
                .disposedBy(disposeBag)
    }

    private fun initMovies(): Observable<PagedList<Movie>> {
        return movieRepository.listingObservable.flatMap { listing -> listing.pagedList }
    }

    private fun initSearchingStatus(): Observable<NetworkStatus> {
        return movieRepository.listingObservable.flatMap { listing -> listing.networkState }
    }

    //endregion

    fun getTextError(error: Throwable): String {
        return errorManager.getTextError(error)
    }

    class Factory(private val movieRepository: MovieRepository, private val errorManager: ErrorManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SearchFragmentViewModel(movieRepository, errorManager) as T
        }
    }
}
