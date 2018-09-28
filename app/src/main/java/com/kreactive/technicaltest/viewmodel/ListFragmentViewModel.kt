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
import com.kreactive.technicaltest.repository.Listing
import com.kreactive.technicaltest.repository.MovieRepository
import com.kreactive.technicaltest.viewmodel.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class ListFragmentViewModel(private val movieRepository: MovieRepository, private val errorManager: ErrorManager) : BaseViewModel() {

    private val searchText: BehaviorRelay<String> = BehaviorRelay.createDefault("")
    private val searchType: BehaviorRelay<Type?> = BehaviorRelay.createDefault(Type.movie)
    private val searchYear: BehaviorRelay<String?> = BehaviorRelay.createDefault("")
    val listing: Observable<Listing<Movie>>
    val movies: Observable<PagedList<Movie>>
    val searchingStatus: Observable<NetworkStatus>


    //region Init

    init {
        listing = initListing()
        movies = initMovies()
        searchingStatus = initSearchingStatus()
    }

    private fun initListing(): Observable<Listing<Movie>> {
        return Observable.combineLatest(
                arrayOf(searchText,
                        searchType,
                        searchYear)
        ) { list ->
            val text = list[0] as String
            val type = list[1] as Type?
            val year = list[2] as String?
            MovieRepository.SearchDatas(text, type, year)
        }.map {
            movieRepository.search(it)
        }
    }

    private fun initMovies(): Observable<PagedList<Movie>> {
        return listing.flatMap {
            it.pagedList
        }
    }

    private fun initSearchingStatus(): Observable<NetworkStatus> {
        return listing.flatMap {
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
            this.searchText.accept(text)
            type?.let { this.searchType.accept(type) }
            year?.let { this.searchYear.accept(year) }
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
