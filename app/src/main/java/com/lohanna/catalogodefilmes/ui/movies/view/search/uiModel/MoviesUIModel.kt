package com.lohanna.catalogodefilmes.ui.movies.view.search.uiModel

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.lohanna.catalogodefilmes.R
import com.lohanna.catalogodefilmes.data.movies.model.MoviesDataModel
import com.lohanna.catalogodefilmes.ui.base.adapter.ItemClickListener
import com.lohanna.catalogodefilmes.ui.base.model.ItemModel
import com.lohanna.catalogodefilmes.ui.common.uiModel.UIModel

class MoviesUIModel {
    open class MovieItem(
        val movie: MoviesDataModel.Movie
    ): ItemModel

    open class ReloadButton(
        var text: String = "Tentar novamente",
        @DrawableRes val icon: Int? = R.drawable.ic_reload,
        var listener: ItemClickListener? = null,
        var margin: UIModel.Margins = UIModel.Margins(
            R.dimen.dimen_16dp,
            R.dimen.dimen_8dp,
            R.dimen.dimen_16dp,
            R.dimen.dimen_8dp
        )
    ): ItemModel

    open class EmptyList(
        @DrawableRes val icon: Int = R.drawable.ic_search_not_found,
        @StringRes val textMessage: Int = R.string.unavailable_resource_message,
        val visibilityIcon: Boolean = true
    ): ItemModel
}