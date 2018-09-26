package com.kreactive.technicaltest.ui.fragment.base

import android.os.Bundle
import androidx.core.content.ContextCompat
import android.view.View
import androidx.fragment.app.Fragment
import com.kreactive.technicaltest.ui.activity.MainActivity
import com.kreactive.technicaltest.utils.RxLifecycleDelegate
import io.reactivex.Observable
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

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
