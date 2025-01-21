package com.lohanna.catalogodefilmes.ui.base.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lohanna.catalogodefilmes.ui.base.adapter.ItemClickListener
import com.lohanna.catalogodefilmes.ui.base.model.ItemModel

abstract class BaseViewHolder<T: ItemModel>(view: View): RecyclerView.ViewHolder(view) {
    abstract fun bind(item: T, listener: ItemClickListener?)
}