package com.kreactive.technicaltest.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.kreactive.technicaltest.viewmodel.base.BaseViewModel

class MainActivityViewModel : BaseViewModel() {

    class Factory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel() as T
        }
    }
}