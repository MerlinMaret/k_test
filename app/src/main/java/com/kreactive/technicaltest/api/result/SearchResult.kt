package com.kreactive.technicaltest.api.result

import com.google.gson.annotations.SerializedName
import com.kreactive.technicaltest.error.SearchErrorNoResult
import com.kreactive.technicaltest.error.SearchErrorTooManyResult
import com.kreactive.technicaltest.model.Movie

data class SearchResult(
        @SerializedName("Search")
        val searchResult: List<Movie>?,
        @SerializedName("Response")
        val response: Boolean,
        @SerializedName("Error")
        private val error: String?
) {

    val tooManyResultErrorText = "Too many results."
    val noResultErrorText = "not found!"

    fun getError(): Throwable? {
        if (error?.contains(tooManyResultErrorText, ignoreCase = true) ?: false) {
            return SearchErrorTooManyResult()
        } else if (error?.contains(noResultErrorText, ignoreCase = true) ?: false) {
            return SearchErrorNoResult()
        } else if (error != null) {
            return Throwable()
        } else {
            return null
        }
    }
}