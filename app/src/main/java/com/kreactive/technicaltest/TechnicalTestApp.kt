package com.kreactive.technicaltest

import android.app.Application
import android.content.Context
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class TechnicalTestApp : Application(), KodeinAware {

    override val kodein = Kodein.lazy {

        bind<Application>() with singleton { this@TechnicalTestApp }
        bind<Context>() with singleton { instance<Application>() }

    }

    override fun onCreate() {
        super.onCreate()
    }
}
