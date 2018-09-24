package com.kreactive.technicaltest.api.result

import android.media.Rating
import com.google.gson.annotations.SerializedName

data class DetailResult(

        @SerializedName("Error")
        val error: String?,

        @SerializedName("Rated")
        val rated: String?,

        @SerializedName("Released")
        val released: String?,

        @SerializedName("Runtime")
        val runtime: String?,

        @SerializedName("Genre")
        val genre: String?,

        @SerializedName("Director")
        val director: String?,

        @SerializedName("Writer")
        val writer: String?,

        @SerializedName("Actors")
        val actors: String?,

        @SerializedName("Plot")
        val plot: String?,

        @SerializedName("Language")
        val language: String?,

        @SerializedName("Country")
        val country: String?,

        @SerializedName("Awards")
        val awards: String?,

        @SerializedName("Ratings")
        val ratings: List<Rating>?,

        @SerializedName("Metascore")
        val metascore: String?,

        @SerializedName("imdbRating")
        val imdbRating: String?,

        @SerializedName("imdbVotes")
        val imdbVotes: String?,

        @SerializedName("DVD")
        val DVD: String?,

        @SerializedName("BoxOffice")
        val boxOffice: String?,

        @SerializedName("Production")
        val production: String?,

        @SerializedName("Website")
        val website: String?
)