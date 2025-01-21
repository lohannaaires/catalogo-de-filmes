package com.lohanna.catalogodefilmes.ui.movies.view.search.viewHolder

import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.lohanna.catalogodefilmes.databinding.EmptyListItemBinding
import com.lohanna.catalogodefilmes.ui.base.adapter.ItemClickListener
import com.lohanna.catalogodefilmes.ui.base.viewHolder.BaseViewHolder
import com.lohanna.catalogodefilmes.ui.movies.view.search.uiModel.MoviesUIModel

class EmptyListViewHolder(
    private val binding: EmptyListItemBinding
): BaseViewHolder<MoviesUIModel.EmptyList>(binding.root) {
    override fun bind(item: MoviesUIModel.EmptyList, listener: ItemClickListener?) {
        binding.ivIconNotFound.setImageDrawable(
            ResourcesCompat.getDrawable(
                binding.root.resources,
                item.icon,
                null
            )
        )

        binding.ivIconNotFound.visibility = if(item.visibilityIcon) View.VISIBLE else View.GONE

        binding.tvMessage.setText(item.textMessage)
    }
}