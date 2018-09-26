package com.kreactive.technicaltest.dependencyinjection

import androidx.lifecycle.ViewModelProviders
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.kreactive.technicaltest.viewmodel.DetailFragmentViewModel
import com.kreactive.technicaltest.viewmodel.ListFragmentViewModel
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

    //region Fragment

    bind<ListFragmentViewModel.Factory>() with provider { ListFragmentViewModel.Factory(instance(), instance()) }
    bind<ListFragmentViewModel>() with factory { fragment: Fragment ->
        ViewModelProviders.of(fragment, instance<ListFragmentViewModel.Factory>())
                .get(ListFragmentViewModel::class.java)
    }

    bind<DetailFragmentViewModel.Factory>() with provider { DetailFragmentViewModel.Factory(instance(), instance()) }
    bind<DetailFragmentViewModel>() with factory { fragment: Fragment ->
        ViewModelProviders.of(fragment, instance<DetailFragmentViewModel.Factory>())
                .get(DetailFragmentViewModel::class.java)
    }

    //endregion
}