package com.kreactive.technicaltest.viewmodel.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel: ViewModel() {
    protected val disposeBag = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }
}