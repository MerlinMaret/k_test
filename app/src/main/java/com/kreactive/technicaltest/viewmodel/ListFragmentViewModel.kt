package com.kreactive.technicaltest.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.jakewharton.rxrelay2.BehaviorRelay
import com.kreactive.technicaltest.model.Movie
import com.kreactive.technicaltest.repository.MovieRepository
import com.kreactive.technicaltest.viewmodel.base.BaseViewModel
import io.reactivex.Observable

class ListFragmentViewModel(private val movieRepository: MovieRepository) : BaseViewModel(){

    private val movies : BehaviorRelay<List<Movie>> = BehaviorRelay.createDefault(emptyList())
    val moviesObservable : Observable<List<Movie>> = movies.share()
    val emptyMoviesObservable : Observable<Boolean> = movies.map { it.size > 0 }

    init {
        //TODO remove
        movies.accept(movieRepository.movies.value)
    }

    fun search(title : String?){
        //TODO add search
        if(title != null){
            val list = movieRepository.movies.value.filter { it.title.contains(title,ignoreCase = true) }
            movies.accept(list)
        }
    }
    
    class Factory(private val movieRepository: MovieRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ListFragmentViewModel(movieRepository) as T
        }
    }
}
