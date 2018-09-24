package com.kreactive.technicaltest.manager

import android.widget.TextView
import com.jakewharton.rxbinding2.widget.textChanges
import com.jakewharton.rxrelay2.Relay
import com.kreactive.technicaltest.utils.RxLifecycleDelegate
import com.kreactive.technicaltest.utils.nonEmittingText
import io.reactivex.Observable
import timber.log.Timber

class ViewBinderManager {
    companion object {
        fun bindTextView(lifecycle: Observable<RxLifecycleDelegate.LifecycleEvent>, textView: TextView, variable: Relay<String>) {
            variable.takeUntil(lifecycle)
                    .subscribe(textView.nonEmittingText())

            textView.textChanges()
                    .takeUntil(lifecycle)
                    .map { it.toString() }
                    .subscribe(variable)
        }

        fun <T> subscribeValue(
                lifecycle: Observable<RxLifecycleDelegate.LifecycleEvent>,
                variable: Observable<T>,
                onNext: (t: T) -> Unit,
                onComplete: () -> Unit = { Timber.i("onComplete") },
                onError: (throwable: Throwable) -> Unit = { Timber.e(it) }) {
            variable.takeUntil(lifecycle)
                    .subscribe(onNext, onError, onComplete)
        }
    }
}