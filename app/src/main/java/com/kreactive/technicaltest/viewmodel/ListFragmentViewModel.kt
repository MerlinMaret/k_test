package com.kreactive.technicaltest.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.text.TextUtils
import com.jakewharton.rxrelay2.BehaviorRelay
import com.kreactive.technicaltest.api.NetworkStatus
import com.kreactive.technicaltest.manager.ErrorManager
import com.kreactive.technicaltest.model.Movie
import com.kreactive.technicaltest.repository.MovieRepository
import com.kreactive.technicaltest.viewmodel.base.BaseViewModel
import io.reactivex.Observable
import timber.log.Timber

class ListFragmentViewModel(private val movieRepository: MovieRepository, private val errorManager: ErrorManager) : BaseViewModel() {

    private val movies: BehaviorRelay<List<Movie>> = movieRepository.movies
    val moviesObservable: Observable<List<Movie>> = movies.share()
    val searchingStatus: BehaviorRelay<NetworkStatus> = BehaviorRelay.createDefault(NetworkStatus.Idle)
    private var searchText: String? = null

    fun reload() {
        search(searchText)
    }

    fun search(searchText: String?) {
        this.searchText = searchText
        if (!TextUtils.isEmpty(searchText)) {
            movieRepository.search(searchText!!)
                    .subscribe(
                            { searchingStatus.accept(it) },
                            {
                                searchingStatus.accept(NetworkStatus.Error(it))
                                Timber.e(it)
                            }
                    )
        }
    }

    fun getTextError(error: Throwable): String {
        return errorManager.getTextError(error)
    }

    class Factory(private val movieRepository: MovieRepository, private val errorManager: ErrorManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ListFragmentViewModel(movieRepository, errorManager) as T
        }
    }
}
