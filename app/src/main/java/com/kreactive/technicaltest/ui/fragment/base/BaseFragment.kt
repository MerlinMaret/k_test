package com.kreactive.technicaltest.ui.fragment.base

import android.support.v4.app.Fragment
import com.kreactive.technicaltest.utils.RxLifecycleDelegate
import io.reactivex.Observable
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.closestKodein

abstract class BaseFragment : Fragment(), KodeinAware {

    private val rxDelegate = RxLifecycleDelegate()
    override val kodein by closestKodein()


    override fun onDestroyView() {
        super.onDestroyView()
        rxDelegate.onFragmentDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
        rxDelegate.onFragmentDetach()
    }

    override fun onPause() {
        super.onPause()
        rxDelegate.onFragmentPause()
    }

    override fun onStop() {
        super.onStop()
        rxDelegate.onFragmentStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        rxDelegate.onFragmentDestroy()
    }

    protected fun lifecycle(event: RxLifecycleDelegate.FragmentEvent): Observable<RxLifecycleDelegate.LifecycleEvent> {
        return rxDelegate.lifecycle(event)
    }
}
