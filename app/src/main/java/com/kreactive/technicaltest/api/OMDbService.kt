package com.kreactive.technicaltest.api

import com.kreactive.technicaltest.api.result.DetailResult
import com.kreactive.technicaltest.api.result.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

const val myApiKey = "4e870274"
interface OMDbService {

    @GET("/")
    fun search(
            @Query("s") search: String,
            @Query("apiKey") apiKey : String = myApiKey,
            @Query("type") type : String?,
            @Query("y") year : String?,
            @Query("page") page : Int = 1
    ): Observable<SearchResult>

    @GET("/")
    fun getMovie(
            @Query("i") search: String,
            @Query("apiKey") apiKey : String = myApiKey,
            @Query("plot") type : String? = "full"
    ): Observable<DetailResult>
}
