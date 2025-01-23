package com.lohanna.catalogodefilmes.ui.movies.viewModel

import androidx.lifecycle.LiveData
import com.lohanna.catalogodefilmes.data.movies.model.MovieDetailsDataModel
import com.lohanna.catalogodefilmes.data.movies.model.MoviesDataModel

interface MoviesSearchViewModel {
    val movies: LiveData<List<MoviesDataModel.Movie>?>
    val movieDetails: LiveData<MovieDetailsDataModel.Response?>
    val error: LiveData<String?>

    fun getMoviesByTerm(term: String)
    fun getMovieDetails(imdb: String)
    fun cleanLoadedData()
}