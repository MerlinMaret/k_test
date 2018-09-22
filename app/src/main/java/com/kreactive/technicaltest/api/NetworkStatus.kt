package com.kreactive.technicaltest.api

sealed class NetworkStatus{

    object Idle : NetworkStatus()
    object InProgress : NetworkStatus()
    object Success : NetworkStatus()
    data class Error<T : Throwable> (val error: T) : NetworkStatus()

}