package com.lohanna.catalogodefilmes.ui.movies.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lohanna.catalogodefilmes.data.movies.model.MoviesDataModel
import com.lohanna.catalogodefilmes.ui.base.model.ItemModel
import com.lohanna.catalogodefilmes.ui.movies.view.search.uiModel.MoviesUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieSearchLayoutViewModelImp @Inject constructor() : ViewModel(),
    MovieSearchLayoutViewModel {
    private val _layout = MutableLiveData<List<ItemModel>>().apply { listOf<ItemModel>() }

    override val layout: LiveData<List<ItemModel>>
        get() = _layout

    override fun createLayout(data: List<MoviesDataModel.Movie>) {
        val rows: MutableList<ItemModel> = mutableListOf()

        if (data.isNotEmpty()) {
            rows.addAll(
                data.map {
                    MoviesUIModel.MovieItem(it)
                }
            )
        } else {
            rows.add(
                MoviesUIModel.ErrorItem(textMessage = "Teste")
            )
        }

        _layout.postValue(rows)
    }
}