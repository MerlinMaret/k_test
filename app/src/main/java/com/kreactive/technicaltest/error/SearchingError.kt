package com.kreactive.technicaltest.error

class SearchErrorTooManyResult(cause: Throwable? = null) : Throwable("Too many results",cause)

class SearchErrorNoResult(cause: Throwable? = null) : Throwable("No result",cause)