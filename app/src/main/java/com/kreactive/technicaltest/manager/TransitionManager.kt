package com.kreactive.technicaltest.manager

import android.content.Context
import android.transition.Fade
import android.transition.Slide
import android.transition.TransitionInflater
import android.transition.TransitionSet
import android.view.Gravity
import android.view.animation.AccelerateDecelerateInterpolator

class TransitionManager() {
    companion object {

        fun getSlideTransition(startDelay: Long, duration : Long, gravity: Int = Gravity.LEFT): Any {
            val slide = Slide(gravity)
            slide.startDelay = startDelay
            slide.duration = duration
            return slide
        }

        fun getSharedElementsTransition(startDelay: Long, duration : Long, context: Context): TransitionSet {
            val enterTransitionSet = TransitionSet()
            enterTransitionSet.addTransition(TransitionInflater.from(context).inflateTransition(android.R.transition.move))
            enterTransitionSet.interpolator = AccelerateDecelerateInterpolator()
            enterTransitionSet.duration = duration
            enterTransitionSet.startDelay = startDelay
            return enterTransitionSet
        }
    }
}