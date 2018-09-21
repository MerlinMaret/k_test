package com.kreactive.technicaltest.repository

import com.jakewharton.rxrelay2.BehaviorRelay
import com.kreactive.technicaltest.model.Movie
import com.kreactive.technicaltest.model.Type

class MovieRepository(){

    val movies : BehaviorRelay<List<Movie>>

    init {

        //TODO remove this test

        val list = ArrayList<Movie>()
        for (i in 0 .. 10){
            list.add(
                    Movie("Batman", "2016", "tt4853102".plus(i), Type.movie, "https://m.media-amazon.com/images/M/MV5BMTdjZTliODYtNWExMi00NjQ1LWIzN2MtN2Q5NTg5NTk3NzliL2ltYWdlXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg")
            )
        }
        movies = BehaviorRelay.createDefault(list)
    }
}