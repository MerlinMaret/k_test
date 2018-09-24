package com.kreactive.technicaltest.dependencyinjection

import com.kreactive.technicaltest.manager.ErrorManager
import com.kreactive.technicaltest.repository.MovieRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val errorModule = Kodein.Module("error") {
    bind<ErrorManager>() with singleton { ErrorManager(instance()) }
}