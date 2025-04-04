package com.lohanna.catalogodefilmes.ui.movies.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lohanna.catalogodefilmes.R
import com.lohanna.catalogodefilmes.databinding.ErrorListItemBinding
import com.lohanna.catalogodefilmes.databinding.MovieItemBinding
import com.lohanna.catalogodefilmes.ui.base.adapter.BaseAdapter
import com.lohanna.catalogodefilmes.ui.movies.view.search.uiModel.MoviesUIModel
import com.lohanna.catalogodefilmes.ui.movies.view.search.viewHolder.ErrorItemViewHolder
import com.lohanna.catalogodefilmes.ui.movies.view.search.viewHolder.MovieItemViewHolder

class MoviesSearchAdapter : BaseAdapter() {
    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is MoviesUIModel.MovieItem -> R.layout.movie_item
            is MoviesUIModel.ErrorItem -> R.layout.error_list_item
            else -> 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.movie_item -> MovieItemViewHolder(
                MovieItemBinding.inflate(inflater, parent, false)
            )

            else -> ErrorItemViewHolder(
                ErrorListItemBinding.inflate(inflater, parent, false)
            )
        }
    }
}