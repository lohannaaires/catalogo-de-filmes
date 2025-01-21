package com.lohanna.catalogodefilmes.ui.base.adapter

import com.lohanna.catalogodefilmes.ui.base.model.ItemModel

interface ItemClickListener {
    fun onItemClicked(item: ItemModel)
}