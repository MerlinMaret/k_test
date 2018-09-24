package com.kreactive.technicaltest.dependencyinjection

import com.kreactive.technicaltest.repository.MovieRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton


val repoModule = Kodein.Module("repo") {
    bind<MovieRepository>() with singleton { MovieRepository(instance()) }
}