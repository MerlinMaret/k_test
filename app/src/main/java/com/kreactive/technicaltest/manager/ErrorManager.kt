package com.kreactive.technicaltest.manager

import android.content.Context
import com.kreactive.technicaltest.R
import com.kreactive.technicaltest.error.SearchErrorNoResult
import com.kreactive.technicaltest.error.SearchErrorTooManyResult

class ErrorManager(val context : Context){

    fun getTextError(error : Throwable) : String{
        when(error){
            is SearchErrorNoResult -> return context.getString(R.string.error_no_result)
            is SearchErrorTooManyResult -> return context.getString(R.string.error_too_many_result)
            else -> return context.getString(R.string.error_unknow)
        }
    }
}