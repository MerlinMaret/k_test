package com.kreactive.technicaltest.utils

import android.widget.TextView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

//region Textview

fun TextView.nonEmittingText(): (string: String) -> Unit {
    return {
        if (text.toString() != it.toString()) {
            text = it
        }
    }
}

//endregion

//region Disposable

fun Disposable.disposedBy(disposeBag: CompositeDisposable) {
    disposeBag.add(this)
}

//endregion