package com.kreactive.technicaltest.ui.fragment.base

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.View
import com.kreactive.technicaltest.R
import com.kreactive.technicaltest.ui.activity.MainActivity
import com.kreactive.technicaltest.utils.RxLifecycleDelegate
import io.reactivex.Observable
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.closestKodein

abstract class BaseFragment : Fragment(), KodeinAware {

    private val rxDelegate = RxLifecycleDelegate()
    override val kodein by closestKodein()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFab()
    }

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

    open fun setFab(){
        setFab(null, null)
    }

    protected fun setFab(icon : Int?, onFabClick : (() -> Unit)?){
        val noFab = icon == null
        if(noFab){
            (activity as? MainActivity)?.hideFab()
        }
        else{
            (activity as? MainActivity)?.showFab(ContextCompat.getDrawable(activity!!,icon!!), onFabClick)
        }
    }

    protected fun lifecycle(event: RxLifecycleDelegate.FragmentEvent): Observable<RxLifecycleDelegate.LifecycleEvent> {
        return rxDelegate.lifecycle(event)
    }
}
