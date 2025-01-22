package com.lohanna.catalogodefilmes.data.movies.model

import com.google.gson.annotations.SerializedName

object MoviesDataModel {
    data class Query(
        @SerializedName("s") val searchTerm: String? = null,
        @SerializedName("type") val type: String? = null,
        @SerializedName("y") val year: Int? = null
    )

    data class Response(
        @SerializedName("Search") val searchList: List<Movie>?,
        @SerializedName("totalResults") val total: String?,
        @SerializedName("Response") val response: String,
        @SerializedName("Error") val error: String?
    )

    data class Movie(
        @SerializedName("Title") val title: String,
        @SerializedName("Year") val year: String,
        @SerializedName("imdbID") val imdbID: String,
        @SerializedName("Type") val type: String,
        @SerializedName("Poster") val poster: String
    )
}