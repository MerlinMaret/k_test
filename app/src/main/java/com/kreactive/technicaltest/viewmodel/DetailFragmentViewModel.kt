package com.kreactive.technicaltest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kreactive.technicaltest.manager.ErrorManager
import com.kreactive.technicaltest.model.Movie
import com.kreactive.technicaltest.repository.MovieRepository
import com.kreactive.technicaltest.utils.disposedBy
import com.kreactive.technicaltest.viewmodel.base.BaseViewModel
import io.reactivex.Observable
import timber.log.Timber

class DetailFragmentViewModel(private val movieRepository: MovieRepository, private val errorManager: ErrorManager) : BaseViewModel() {
/*
    var movieId: String? = null
    val movie: Observable<Movie> = movieRepository.movies.map { it.find { it.imdbID == movieId } }

    fun loadDatas(movieId: String?) {
        this.movieId = movieId

        movie.subscribe {
            if (it.rated == null) {
                getDetails(it)
            }
        }.disposedBy(disposeBag)
    }

    private fun getDetails(movie: Movie) {
        movieRepository
                .getMovie(movie)
                .subscribe {
                    Timber.i(movie.toString())
                }
    }
*/
    class Factory(private val movieRepository: MovieRepository, private val errorManager: ErrorManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return DetailFragmentViewModel(movieRepository, errorManager) as T
        }
    }
}