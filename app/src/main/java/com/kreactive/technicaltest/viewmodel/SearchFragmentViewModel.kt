package com.kreactive.technicaltest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.text.TextUtils
import com.jakewharton.rxrelay2.BehaviorRelay
import com.kreactive.technicaltest.api.NetworkStatus
import com.kreactive.technicaltest.manager.ErrorManager
import com.kreactive.technicaltest.model.Movie
import com.kreactive.technicaltest.model.Type
import com.kreactive.technicaltest.repository.MovieRepository
import com.kreactive.technicaltest.viewmodel.base.BaseViewModel
import io.reactivex.Observable
import rx.Subscription
import timber.log.Timber

class SearchFragmentViewModel(private val movieRepository: MovieRepository, private val errorManager: ErrorManager) : BaseViewModel() {
/*
    private val movies: BehaviorRelay<List<Movie>> = movieRepository.movies
    val moviesObservable: Observable<List<Movie>> = movies.share()
    val searchingStatus: BehaviorRelay<NetworkStatus> = BehaviorRelay.createDefault(NetworkStatus.Idle)
    var searchText: String? = null
    var type: Type? = null
    var year: String? = null
    var searchSubscription : Subscription? = null

    fun search(
            searchText: String? = this.searchText,
            type: Type? = this.type,
            year: String? = this.year,
            needReload: Boolean = false
    ) {
        val isSameText = this.searchText == searchText
        val isSameType = this.type == type
        val isSameYear = this.year == year
        val isTextEmpty = TextUtils.isEmpty(searchText)

        this.searchText = searchText
        this.type = type
        this.year = year

        val needWSCall = (!(isSameText && isSameType && isSameYear)) || needReload

        if (!isTextEmpty && needWSCall) {
            searchSubscription?.unsubscribe()
            searchingStatus.accept(NetworkStatus.Idle)
            searchSubscription = movieRepository
                    .search(searchText!!, type, year)
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
*/
    class Factory(private val movieRepository: MovieRepository, private val errorManager: ErrorManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SearchFragmentViewModel(movieRepository, errorManager) as T
        }
    }
}
