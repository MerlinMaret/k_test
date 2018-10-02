package com.kreactive.technicaltest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.util.Log
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

class ListFragmentViewModel(private val movieRepository: MovieRepository, private val errorManager: ErrorManager) : BaseViewModel() {

    private val searchTextRelay: BehaviorRelay<String> = movieRepository.searchTextRelay
    private val searchTypeRelay: BehaviorRelay<Type?> = movieRepository.searchTypeRelay
    private val searchYearRelay: BehaviorRelay<String?> = movieRepository.searchYearRelay
    val movies: Observable<PagedList<Movie>>
    val searchingStatus: Observable<NetworkStatus>

    //region Init

    init {
        initSearch()
        movies = initMovies()
        searchingStatus = initSearchingStatus()
    }

    private fun initSearch() {
        Observable.combineLatest(
                arrayOf(searchTextRelay.share(),
                        searchTypeRelay.share(),
                        searchYearRelay.share())
        ) { list ->
            val searchText = list[0] as String
            val searchType = list[1] as Type?
            val searchYear = list[2] as String?
            val searchData = MovieRepository.SearchDatas(searchText, searchType, searchYear)
            movieRepository.search(searchData)
        }
                .subscribe()
                .disposedBy(disposeBag)
    }

    private fun initMovies(): Observable<PagedList<Movie>> {
        return movieRepository.pagedListObservable
    }

    private fun initSearchingStatus(): Observable<NetworkStatus> {
        return movieRepository.pagedListNetworkStatusObservable
    }

    //endregion

    fun reload() {
        movieRepository.reload()
    }

    fun setSearchText(searchText: String) {
        try {
            val lastText = this.searchTextRelay.value
            val isSameText = lastText == searchText
            if (!isSameText) {
                this.searchTextRelay.accept(searchText)
            }
        } catch (e: Throwable) {
            Log.e("text search", e.toString())
        }
    }

    fun setSearchType(type: Type) {
        val lastType = getType()
        val isSameType = lastType == type
        if (!isSameType) {
            this.searchTypeRelay.accept(type)
        }
    }

    fun setSearchYear(year: String) {
        val lastYear = getYear()
        val isSameYear = lastYear == year
        if (!isSameYear) {
            this.searchYearRelay.accept(year)
        }
    }

    fun getTextError(error: Throwable): String {
        return errorManager.getTextError(error)
    }

    fun getType(): Type? {
        return this.searchTypeRelay.value
    }

    fun getYear(): String? {
        return this.searchYearRelay.value
    }

    class Factory(private val movieRepository: MovieRepository, private val errorManager: ErrorManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ListFragmentViewModel(movieRepository, errorManager) as T
        }
    }
}
