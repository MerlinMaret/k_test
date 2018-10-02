package com.kreactive.technicaltest.repository

import androidx.paging.PagedList
import com.jakewharton.rxrelay2.BehaviorRelay
import com.kreactive.technicaltest.api.NetworkStatus
import io.reactivex.Observable

data class Listing<T>(
        val pagedList: Observable<PagedList<T>>,
        val networkState: Observable<NetworkStatus>,
        val refresh: () -> Unit,
        val retry: () -> Unit)