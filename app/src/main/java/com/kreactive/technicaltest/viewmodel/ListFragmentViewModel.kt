package com.kreactive.technicaltest.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.jakewharton.rxrelay2.BehaviorRelay
import com.kreactive.technicaltest.model.Movie
import com.kreactive.technicaltest.repository.MovieRepository
import com.kreactive.technicaltest.viewmodel.base.BaseViewModel
import io.reactivex.Observable

class ListFragmentViewModel(private val movieRepository: MovieRepository) : BaseViewModel(){

    val moviesObservable : Observable<List<Movie>> = movieRepository.movies.share()

    class Factory(private val movieRepository: MovieRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ListFragmentViewModel(movieRepository) as T
        }
    }
}
