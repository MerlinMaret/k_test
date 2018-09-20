package com.kreactive.technicaltest.dependencyinjection

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import com.kreactive.technicaltest.ui.activity.MainActivity
import com.kreactive.technicaltest.viewmodel.MainActivityViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val viewModelModule = Kodein.Module("viewModel") {

    //region Activity

    bind<MainActivityViewModel.Factory>() with provider { MainActivityViewModel.Factory() }
    bind<MainActivityViewModel>() with factory { activity: AppCompatActivity ->
        ViewModelProviders.of(activity, instance<MainActivityViewModel.Factory>())
                .get(MainActivityViewModel::class.java)
    }

    //endregion
}