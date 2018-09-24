package com.kreactive.technicaltest.manager

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
    }
}