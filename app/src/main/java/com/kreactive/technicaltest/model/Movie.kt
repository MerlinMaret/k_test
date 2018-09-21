package com.kreactive.technicaltest.model

import com.google.gson.annotations.SerializedName

data class Movie(
        @SerializedName("Title")
        val title: String,
        @SerializedName("Year")
        val year: String,
        @SerializedName("imdbID")
        val imdbID: String,
        @SerializedName("Type")
        val type: Type,
        @SerializedName("Poster")
        val posterUrl: String
) {

    override fun equals(other: Any?): Boolean {
        val isEquals: Boolean
        isEquals = if (other is Movie) { imdbID.equals(other.imdbID) } else { false }
        return isEquals
    }
}