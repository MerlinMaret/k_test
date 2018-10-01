package com.kreactive.technicaltest.manager

import com.kreactive.technicaltest.utils.RxLifecycleDelegate
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ViewBinderManager {
    companion object {
        fun <T> subscribeValue(
                lifecycle: Observable<RxLifecycleDelegate.LifecycleEvent>,
                variable: Observable<T>,
                onNext: (t: T) -> Unit,
                onComplete: () -> Unit = { Timber.i("onComplete") },
                onError: (throwable: Throwable) -> Unit = { Timber.e(it) }): Disposable {
            return variable.takeUntil(lifecycle)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onNext, onError, onComplete)
        }
    }
}