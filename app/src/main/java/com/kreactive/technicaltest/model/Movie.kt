package com.kreactive.technicaltest.model

import android.media.Rating
import com.google.gson.annotations.SerializedName

@Ent
data class Movie(
        @SerializedName("Title")
        var title: String,
        @SerializedName("Year")
        var year: String,
        @SerializedName("imdbID")
        var imdbID: String,
        @SerializedName("Poster")
        var posterUrl: String,
        @SerializedName("Rated")
         var rated: String?,
        @SerializedName("Released")
         var released: String?,
        @SerializedName("Runtime")
         var runtime: String?,
        @SerializedName("Genre")
         var genre: String?,
        @SerializedName("Director")
         var director: String?,
        @SerializedName("Writer")
         var writer: String?,
        @SerializedName("Actors")
         var actors: String?,
        @SerializedName("Plot")
         var plot: String?,
        @SerializedName("Language")
         var language: String?,
        @SerializedName("Country")
         var country: String?,
        @SerializedName("Awards")
         var awards: String?,
        @SerializedName("Ratings")
         var ratings: List<Rating>?,
        @SerializedName("Metascore")
         var metascore: String?,
        @SerializedName("imdbRating")
         var imdbRating: String?,
        @SerializedName("imdbVotes")
         var imdbVotes: String?,
        @SerializedName("Type")
         var type: Type?,
        @SerializedName("DVD")
         var DVD: String?,
        @SerializedName("BoxOffice")
         var boxOffice: String?,
        @SerializedName("Production")
         var production: String?,
        @SerializedName("Website")
         var website: String?
) {

    override fun equals(other: Any?): Boolean {
        val isEquals: Boolean
        isEquals = if (other is Movie) { imdbID.equals(other.imdbID) } else { false }
        return isEquals
    }

    fun needLoadDatas(): Boolean {
        return runtime == null
    }
}