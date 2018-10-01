package com.kreactive.technicaltest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.text.TextUtils
import androidx.paging.PagedList
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.kreactive.technicaltest.api.NetworkStatus
import com.kreactive.technicaltest.manager.ErrorManager
import com.kreactive.technicaltest.model.Movie
import com.kreactive.technicaltest.model.Type
import com.kreactive.technicaltest.repository.Listing
import com.kreactive.technicaltest.repository.MovieRepository
import com.kreactive.technicaltest.utils.disposedBy
import com.kreactive.technicaltest.viewmodel.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

class ListFragmentViewModel(private val movieRepository: MovieRepository, private val errorManager: ErrorManager) : BaseViewModel() {

    private val searchText: PublishRelay<String> = PublishRelay.create()
    private val searchType: PublishRelay<Type?> = PublishRelay.create()
    private val searchYear: PublishRelay<String?> = PublishRelay.create()
    val movies: Observable<PagedList<Movie>>
    val searchingStatus: Observable<NetworkStatus>
    private var searchDisposable : Disposable


    //region Init

    init {
        val searchObservable = initSearch()
        movies = initMovies(searchObservable)
        searchingStatus = initSearchingStatus(searchObservable)
        searchDisposable = searchObservable.subscribe()
        searchDisposable.disposedBy(disposeBag)
    }

    private fun initSearch() : Observable<Listing<Movie>>{
        return Observable.combineLatest(
                arrayOf(searchText,
                        searchType,
                        searchYear)
        ) { list ->
            val text = list[0] as String
            val type = list[1] as Type?
            val year = list[2] as String?
            val searchData = MovieRepository.SearchDatas(text, type, year)
            movieRepository.search(searchData)
        }
    }

    private fun initMovies(searchObservable : Observable<Listing<Movie>>): Observable<PagedList<Movie>> {
        return searchObservable.flatMap {
            it.pagedList
        }
    }

    private fun initSearchingStatus(searchObservable : Observable<Listing<Movie>>): Observable<NetworkStatus> {
        return searchObservable.flatMap {
            it.networkState
        }
    }

    //endregion

    fun reload() {
        movieRepository.reload()
    }

    fun search(
            text: String? = this.searchText.value,
            type: Type? = this.searchType.value,
            year: String? = this.searchYear.value,
            needReload: Boolean = false
    ) {
        val isSameText = this.searchText.value == text
        val isSameType = this.searchType.value == type
        val isSameYear = this.searchYear.value == year
        val isTextEmpty = TextUtils.isEmpty(text)

        val needWSCall = (!(isSameText && isSameType && isSameYear)) || needReload
        if (!isTextEmpty && needWSCall) {
            if(!isSameText) { this.searchText.accept(text) }
            if(!isSameType) { this.searchType.accept(type) }
            if(!isSameYear) { this.searchYear.accept(year) }
        }
    }

    fun getTextError(error: Throwable): String {
        return errorManager.getTextError(error)
    }

    fun getType(): Type? {
        return this.searchType.value
    }

    fun getYear(): String? {
        return this.searchYear.value
    }

    class Factory(private val movieRepository: MovieRepository, private val errorManager: ErrorManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ListFragmentViewModel(movieRepository, errorManager) as T
        }
    }
}
