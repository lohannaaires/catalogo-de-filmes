package com.lohanna.catalogodefilmes.data.movies.repository

import com.lohanna.catalogodefilmes.data.common.model.DataState
import com.lohanna.catalogodefilmes.data.movies.model.MovieDetailsDataModel
import com.lohanna.catalogodefilmes.data.movies.model.MoviesDataModel

interface MoviesRepository {
    suspend fun searchMovies(query: MoviesDataModel.Query):
            DataState<MoviesDataModel.Response>

    suspend fun searchMovieDetails(query: MovieDetailsDataModel.Query):
            DataState<MovieDetailsDataModel.Response>
}