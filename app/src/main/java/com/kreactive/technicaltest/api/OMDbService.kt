package com.kreactive.technicaltest.api

import com.kreactive.technicaltest.api.result.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface OMDbService {

    @GET("/")
    fun search(
            @Query("s") search: String,
            @Query("apiKey") apiKey : String = "4e870274",
            @Query("page") page : Int = 1
    ): Observable<SearchResult>

}
