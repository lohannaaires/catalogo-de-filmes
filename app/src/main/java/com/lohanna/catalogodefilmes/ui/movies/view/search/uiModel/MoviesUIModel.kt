package com.lohanna.catalogodefilmes.ui.movies.view.search.uiModel

import androidx.annotation.DrawableRes
import com.lohanna.catalogodefilmes.R
import com.lohanna.catalogodefilmes.data.movies.model.MoviesDataModel
import com.lohanna.catalogodefilmes.ui.base.model.ItemModel

class MoviesUIModel {
    open class MovieItem(
        val movie: MoviesDataModel.Movie
    ) : ItemModel

    open class ErrorItem(
        @DrawableRes val icon: Int = R.drawable.ic_search_not_found,
        val textMessage: String,
        val visibilityIcon: Boolean = true
    ) : ItemModel
}