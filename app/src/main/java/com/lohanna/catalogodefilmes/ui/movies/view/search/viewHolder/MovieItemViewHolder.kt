package com.lohanna.catalogodefilmes.ui.movies.view.search.viewHolder

import com.lohanna.catalogodefilmes.R
import com.lohanna.catalogodefilmes.databinding.MovieItemBinding
import com.lohanna.catalogodefilmes.ui.base.adapter.ItemClickListener
import com.lohanna.catalogodefilmes.ui.base.viewHolder.BaseViewHolder
import com.lohanna.catalogodefilmes.ui.movies.view.search.uiModel.MoviesUIModel
import com.squareup.picasso.Picasso

class MovieItemViewHolder(
    private val binding: MovieItemBinding
): BaseViewHolder<MoviesUIModel.MovieItem>(binding.root) {

    override fun bind(item: MoviesUIModel.MovieItem, listener: ItemClickListener?) {
        item.movie.apply {
            binding.tvMovieTitle.text = binding.root.resources.getString(R.string.movie_title, this.title, this.year)
            binding.tvMovieType.text = this.type
        }

         when(item.movie.type) {
            "movie" -> binding.ivType.setImageResource(R.drawable.ic_movie)
            "series" -> binding.ivType.setImageResource(R.drawable.ic_folder)
        }

        binding.mcvMovie.setOnClickListener {
            listener?.onItemClicked(item)
        }

        Picasso.get()
            .load(item.movie.poster)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .resize(300, 300)
            .centerCrop()
            .into(binding.ivMovie)
    }
}