package com.lohanna.catalogodefilmes.ui.common.uiModel

import android.content.res.Resources
import android.util.TypedValue
import com.lohanna.catalogodefilmes.ui.base.model.ItemModel

object UIModel {
    data class Margins(
        var start: Int = 0,
        var end: Int = 0,
        var top: Int = 0,
        var bottom: Int = 0
    ): ItemModel {
        fun getDP(value: Int): Int = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }
}