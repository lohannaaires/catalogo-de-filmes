package com.lohanna.catalogodefilmes.data.movies.repository

import android.util.Log
import com.google.gson.Gson
import com.lohanna.catalogodefilmes.BuildConfig
import com.lohanna.catalogodefilmes.data.common.model.DataState
import com.lohanna.catalogodefilmes.data.movies.model.ErrorResponse
import com.lohanna.catalogodefilmes.data.movies.model.MovieDetailsDataModel
import com.lohanna.catalogodefilmes.data.movies.model.MoviesDataModel
import com.lohanna.catalogodefilmes.data.movies.remote.MoviesRemoteDataSource
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val remote: MoviesRemoteDataSource) :
    MoviesRepository {

    override suspend fun searchMovies(query: MoviesDataModel.Query):
            DataState<MoviesDataModel.Response> {
        return try {
            val response = remote.getMovies(
                apiKey = BuildConfig.API_KEY,
                searchQuery = query.searchTerm,
                type = query.type,
                year = query.year
            )

            Log.i("REPOSITORY", response.body()?.toString() ?: "")

            response.body()?.let { DataState.Success(it) }
                ?: getError(response.errorBody()?.string())
        } catch (e: Throwable) {
            Log.i("REPOSITORY", genericErrorMessage)
            DataState.Error(Throwable(genericErrorMessage))
        }
    }

    override suspend fun searchMovieDetails(query: MovieDetailsDataModel.Query):
            DataState<MovieDetailsDataModel.Response> {
        return try {
            val response = remote.getMovieDetails(
                apiKey = BuildConfig.API_KEY,
                imdb = query.imdb
            )

            Log.i("REPOSITORY", response.body()?.toString() ?: "")

            response.body()?.let { DataState.Success(it) }
                ?: getError(response.errorBody()?.string())
        } catch (e: Throwable) {
            Log.i("REPOSITORY", genericErrorMessage)
            DataState.Error(Throwable(genericErrorMessage))
        }
    }

    private fun getError(data: String?): DataState.Error {
        val response = Gson().fromJson<Any>(data, ErrorResponse::class.java) as? ErrorResponse
        Log.i("REPOSITORY", response?.error ?: genericErrorMessage)
        return DataState.Error(Throwable(response?.error ?: genericErrorMessage))
    }

    private val genericErrorMessage = "Ocorreu um erro."
}