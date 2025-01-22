package com.lohanna.catalogodefilmes.ui.movies.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lohanna.catalogodefilmes.data.common.model.DataState
import com.lohanna.catalogodefilmes.data.movies.model.MovieDetailsDataModel
import com.lohanna.catalogodefilmes.data.movies.model.MoviesDataModel
import com.lohanna.catalogodefilmes.data.movies.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesSearchViewModelImp @Inject constructor(private val repository: MoviesRepository) :
    ViewModel(), MoviesSearchViewModel {

    private val _movies = MutableLiveData<List<MoviesDataModel.Movie>?>().apply { value = null }
    override val movies: LiveData<List<MoviesDataModel.Movie>?>
        get() = _movies

    private val _movieDetails =
        MutableLiveData<MovieDetailsDataModel.Response?>().apply { value = null }
    override val movieDetails: LiveData<MovieDetailsDataModel.Response?>
        get() = _movieDetails

    private val _error = MutableLiveData<String?>().apply { value = null }
    override val error: LiveData<String?>
        get() = _error

    override fun getMoviesByTerm(term: String) {
        getMovies(MoviesDataModel.Query(searchTerm = term))
    }

    private fun getMovies(query: MoviesDataModel.Query) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = repository.searchMovies(query)) {
                is DataState.Success -> {
                    response.data.let {
                        _movies.postValue(it.searchList)
                    }
                }

                is DataState.Error -> {
                    _error.postValue(response.exception.message)
                }
            }
        }
    }

    override fun getMovieDetails(imdb: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val query = MovieDetailsDataModel.Query(imdb)

            when (val response = repository.searchMovieDetails(query)) {
                is DataState.Success -> {
                    response.data.let {
                        _movieDetails.postValue(it)
                    }
                }

                is DataState.Error -> {
                    _error.postValue(response.exception.message)
                }
            }
        }
    }
}