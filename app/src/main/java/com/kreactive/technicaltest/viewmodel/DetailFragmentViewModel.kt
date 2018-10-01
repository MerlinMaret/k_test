package com.kreactive.technicaltest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxrelay2.PublishRelay
import com.kreactive.technicaltest.manager.ErrorManager
import com.kreactive.technicaltest.model.Movie
import com.kreactive.technicaltest.repository.MovieRepository
import com.kreactive.technicaltest.utils.disposedBy
import com.kreactive.technicaltest.viewmodel.base.BaseViewModel
import io.reactivex.Observable
import timber.log.Timber

class DetailFragmentViewModel(private val movieRepository: MovieRepository, private val errorManager: ErrorManager) : BaseViewModel() {

    var movieId: String? = null
    var movieObservable: PublishRelay<Movie?> = PublishRelay.create()


    fun loadDatas(movieId: String?) {
        this.movieId = movieId
        movieRepository.pagedListObservable.map { pagedList ->
            pagedList.find {
                it.imdbID == movieId
            }
        }
                .subscribe(movieObservable)
                .disposedBy(disposeBag)
    }

    private fun getDetails(movie: Movie?) {
        movie?.let {
            movieRepository
                    .getMovie(movie)
                    .subscribe {
                        Timber.i(movie.toString())
                    }
        }
    }

    class Factory(private val movieRepository: MovieRepository, private val errorManager: ErrorManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return DetailFragmentViewModel(movieRepository, errorManager) as T
        }
    }
}