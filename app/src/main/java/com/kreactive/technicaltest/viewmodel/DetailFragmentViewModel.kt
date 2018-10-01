package com.kreactive.technicaltest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxrelay2.BehaviorRelay
import com.kreactive.technicaltest.manager.ErrorManager
import com.kreactive.technicaltest.model.Movie
import com.kreactive.technicaltest.repository.MovieRepository
import com.kreactive.technicaltest.utils.disposedBy
import com.kreactive.technicaltest.viewmodel.base.BaseViewModel

class DetailFragmentViewModel(private val movieRepository: MovieRepository, private val errorManager: ErrorManager) : BaseViewModel() {

    var movieId: String? = null
    var movieObservable: BehaviorRelay<Movie?> = BehaviorRelay.create()


    fun loadDatas(movieId: String?) {
        this.movieId = movieId
        movieRepository.pagedListObservable.map { pagedList ->
            pagedList.find {
                it.imdbID == movieId
            }
        }
                .subscribe { movie ->
                    movieObservable.accept(movie)
                    if (movie?.needLoadDatas() ?: false) {
                        movie?.let { getDetails(it) }
                    }
                }
                .disposedBy(disposeBag)

    }

    private fun getDetails(movie: Movie) {
        movieRepository
                .getMovie(movie)
                .subscribe { movieObservable.accept(movieObservable.value) }
                .disposedBy(disposeBag)

    }

    class Factory(private val movieRepository: MovieRepository, private val errorManager: ErrorManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return DetailFragmentViewModel(movieRepository, errorManager) as T
        }
    }
}