package com.lohanna.catalogodefilmes.data.movies.remote

import com.lohanna.catalogodefilmes.data.movies.model.MovieDetailsDataModel
import com.lohanna.catalogodefilmes.data.movies.model.MoviesDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesRemoteDataSource {
    @GET(".")
    suspend fun getMovies(
        @Query("apikey") apiKey: String,
        @Query("s") searchQuery: String?,
        @Query("type") type: String?,
        @Query("y") year: Int?,

    ): Response<MoviesDataModel.Response>

    @GET(".")
    suspend fun getMovieDetails(
        @Query("apikey") apiKey: String,
        @Query("i") imdb: String?
    ): Response<MovieDetailsDataModel.Response>
}