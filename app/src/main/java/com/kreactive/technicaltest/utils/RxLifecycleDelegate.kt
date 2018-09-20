package com.kreactive.technicaltest.utils

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxLifecycleDelegate {

    interface LifecycleEvent

    enum class ActivityEvent : LifecycleEvent {
        PAUSE,
        STOP,
        DESTROY
    }

    enum class FragmentEvent : LifecycleEvent{
        PAUSE,
        STOP,
        DESTROY_VIEW,
        DESTROY,
        DETACH
    }

    private val activityLifecycleSubject: PublishSubject<LifecycleEvent> = PublishSubject.create()
    private val fragmentLifecycleSubject: PublishSubject<LifecycleEvent> = PublishSubject.create()

    fun onActivityPause() {
        activityLifecycleSubject.onNext(ActivityEvent.PAUSE)
    }

    fun onActivityStop() {
        activityLifecycleSubject.onNext(ActivityEvent.STOP)
    }

    fun onActivityDestroy() {
        activityLifecycleSubject.onNext(ActivityEvent.DESTROY)
    }

    fun lifecycle(event: ActivityEvent): Observable<LifecycleEvent> {
        return activityLifecycleSubject.filter { it == event }
    }

    fun onFragmentPause() {
        fragmentLifecycleSubject.onNext(FragmentEvent.PAUSE)
    }

    fun onFragmentStop() {
        fragmentLifecycleSubject.onNext(FragmentEvent.STOP)
    }

    fun onFragmentDestroyView() {
        fragmentLifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW)
    }

    fun onFragmentDestroy() {
        fragmentLifecycleSubject.onNext(FragmentEvent.DESTROY)
    }

    fun onFragmentDetach() {
        fragmentLifecycleSubject.onNext(FragmentEvent.DETACH)
    }

    fun lifecycle(event: FragmentEvent): Observable<LifecycleEvent> {
        return fragmentLifecycleSubject.filter { it == event }
    }
}