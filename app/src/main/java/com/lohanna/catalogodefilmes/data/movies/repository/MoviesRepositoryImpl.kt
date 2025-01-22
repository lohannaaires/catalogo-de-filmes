package com.lohanna.catalogodefilmes.data.movies.repository

import android.util.Log
import com.lohanna.catalogodefilmes.BuildConfig
import com.lohanna.catalogodefilmes.data.common.model.DataState
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

            response.body()?.let {
                if (it.response == "True") {
                    Log.i("SUCCESS:", response.body()?.toString() ?: "")
                    DataState.Success(it)
                } else {
                    getError(it.error)
                }
            } ?: getError()

        } catch (e: Throwable) {
            getError()
        }
    }

    override suspend fun searchMovieDetails(query: MovieDetailsDataModel.Query):
            DataState<MovieDetailsDataModel.Response> {
        return try {
            val response = remote.getMovieDetails(
                apiKey = BuildConfig.API_KEY,
                imdb = query.imdb
            )

            response.body()?.let {
                if (it.response == "True") {
                    Log.i("SUCCESS:", response.body()?.toString() ?: "")
                    DataState.Success(it)
                } else {
                    getError(it.error)
                }
            } ?: getError()

        } catch (e: Throwable) {
            getError()
        }
    }

    private fun getError(data: String? = null): DataState.Error {
        Log.i("ERROR:", data ?: genericErrorMessage)
        return DataState.Error(Throwable(data ?: genericErrorMessage))
    }

    private val genericErrorMessage = "An error occurred."
}