package com.lohanna.catalogodefilmes.ui.movies.view.search

import androidx.lifecycle.LiveData
import com.lohanna.catalogodefilmes.data.movies.model.MoviesDataModel
import com.lohanna.catalogodefilmes.ui.base.model.ItemModel

interface MovieSearchLayoutViewModel {
    val layout: LiveData<List<ItemModel>>
    fun createLayout(data: List<MoviesDataModel.Movie>)
}