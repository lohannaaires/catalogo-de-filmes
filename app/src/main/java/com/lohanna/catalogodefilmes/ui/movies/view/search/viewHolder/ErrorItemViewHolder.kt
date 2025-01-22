package com.lohanna.catalogodefilmes.ui.movies.view.search.viewHolder

import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.lohanna.catalogodefilmes.databinding.ErrorListItemBinding
import com.lohanna.catalogodefilmes.ui.base.adapter.ItemClickListener
import com.lohanna.catalogodefilmes.ui.base.viewHolder.BaseViewHolder
import com.lohanna.catalogodefilmes.ui.movies.view.search.uiModel.MoviesUIModel

class ErrorItemViewHolder(
    private val binding: ErrorListItemBinding
) : BaseViewHolder<MoviesUIModel.ErrorItem>(binding.root) {
    override fun bind(item: MoviesUIModel.ErrorItem, listener: ItemClickListener?) {
        binding.ivIconNotFound.setImageDrawable(
            ResourcesCompat.getDrawable(
                binding.root.resources,
                item.icon,
                null
            )
        )

        binding.ivIconNotFound.visibility = if (item.visibilityIcon) View.VISIBLE else View.GONE

        binding.tvMessage.text = item.textMessage
    }
}