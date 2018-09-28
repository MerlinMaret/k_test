package com.kreactive.technicaltest.repository

import androidx.paging.PagedList
import com.jakewharton.rxrelay2.BehaviorRelay
import com.kreactive.technicaltest.api.NetworkStatus
import io.reactivex.Observable

data class Listing<T>(
        // the LiveData of paged lists for the UI to observe
        val pagedList: Observable<PagedList<T>>,
        // represents the network request status to show to the user
        val networkState: Observable<NetworkStatus>,
        // refreshes the whole data and fetches it from scratch.
        val refresh: () -> Unit,
        // retries any failed requests.
        val retry: () -> Unit)