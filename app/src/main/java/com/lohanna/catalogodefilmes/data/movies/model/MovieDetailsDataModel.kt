package com.lohanna.catalogodefilmes.data.movies.model

import com.google.gson.annotations.SerializedName

object MovieDetailsDataModel {
    data class Query(
        @SerializedName("i") val imdb: String? = null
    )

    data class Response(
        @SerializedName("Title") var title: String? = null,
        @SerializedName("Year") var year: String? = null,
        @SerializedName("Rated") var rated: String? = null,
        @SerializedName("Released") var released: String? = null,
        @SerializedName("Runtime") var runtime: String? = null,
        @SerializedName("Genre") var genre: String? = null,
        @SerializedName("Director") var director: String? = null,
        @SerializedName("Writer") var writer: String? = null,
        @SerializedName("Actors") var actors: String? = null,
        @SerializedName("Plot") var plot: String? = null,
        @SerializedName("Language") var language: String? = null,
        @SerializedName("Country") var country: String? = null,
        @SerializedName("Awards") var awards: String? = null,
        @SerializedName("Poster") var poster: String? = null,
        @SerializedName("Ratings") var ratings: ArrayList<Ratings> = arrayListOf(),
        @SerializedName("Metascore") var metascore: String? = null,
        @SerializedName("imdbRating") var imdbRating: String? = null,
        @SerializedName("imdbVotes") var imdbVotes: String? = null,
        @SerializedName("imdbID") var imdbID: String? = null,
        @SerializedName("Type") var type: String? = null,
        @SerializedName("DVD") var dvd: String? = null,
        @SerializedName("BoxOffice") var boxOffice: String? = null,
        @SerializedName("Production") var production: String? = null,
        @SerializedName("Website") var website: String? = null,
        @SerializedName("Response") var response: String? = null,
        @SerializedName("Error") val error: String?
    )

    data class Ratings(
        @SerializedName("Source") var source: String? = null,
        @SerializedName("Value") var value: String? = null
    )
}