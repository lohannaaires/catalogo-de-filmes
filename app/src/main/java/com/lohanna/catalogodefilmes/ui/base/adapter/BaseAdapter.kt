package com.lohanna.catalogodefilmes.ui.base.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.lohanna.catalogodefilmes.ui.base.model.ItemModel
import com.lohanna.catalogodefilmes.ui.base.viewHolder.BaseViewHolder

abstract class BaseAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected var list: MutableList<ItemModel> = mutableListOf()
    var listener: ItemClickListener? = null

    override fun getItemCount(): Int {
        return list.size
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BaseViewHolder<ItemModel>)?.bind(list[position], listener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(list: List<ItemModel>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }
}