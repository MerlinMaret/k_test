package com.kreactive.technicaltest.utils

import android.widget.TextView

//region Textview

fun TextView.nonEmittingText(): (string: String) -> Unit {
    return {
        if (text.toString() != it.toString()) {
            text = it
        }
    }
}

//endregion