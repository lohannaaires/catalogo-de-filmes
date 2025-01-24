package com.lohanna.catalogodefilmes.ui.movies.view.search

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lohanna.catalogodefilmes.R
import com.lohanna.catalogodefilmes.data.movies.model.MoviesDataModel
import com.lohanna.catalogodefilmes.ui.base.model.ItemModel
import com.lohanna.catalogodefilmes.ui.movies.view.search.uiModel.MoviesUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesSearchLayoutViewModelImp @Inject constructor(application: Application) : ViewModel(),
    MoviesSearchLayoutViewModel {
    private val _layout = MutableLiveData<List<ItemModel>>().apply { listOf<ItemModel>() }

    override val layout: LiveData<List<ItemModel>>
        get() = _layout

    val res: Resources = application.resources

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
                MoviesUIModel.ErrorItem(textMessage = res.getString(R.string.generic_error_message))
            )
        }

        _layout.postValue(rows)
    }
}