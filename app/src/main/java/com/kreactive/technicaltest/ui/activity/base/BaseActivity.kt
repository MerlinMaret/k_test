package com.kreactive.technicaltest.ui.activity.base

import android.support.v7.app.AppCompatActivity
import com.kreactive.technicaltest.utils.RxLifecycleDelegate
import io.reactivex.Observable
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

abstract class BaseActivity : AppCompatActivity(), KodeinAware {

    private val rxDelegate = RxLifecycleDelegate()
    override val kodein by closestKodein()

    override fun onPause() {
        super.onPause()
        rxDelegate.onActivityPause()
    }

    override fun onStop() {
        super.onStop()
        rxDelegate.onActivityStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        rxDelegate.onActivityDestroy()
    }

    protected fun lifecycle(event: RxLifecycleDelegate.ActivityEvent): Observable<RxLifecycleDelegate.LifecycleEvent> {
        return rxDelegate.lifecycle(event)
    }
}