package com.lohanna.catalogodefilmes.ui.component

import android.app.Dialog
import android.content.Context
import com.lohanna.catalogodefilmes.R

class Loading(context: Context) : Dialog(context, R.style.Theme_Dialog_Loading) {
    init {
        init()
    }

    private fun init() {
        this.setContentView(R.layout.loading_dialog)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    companion object {
        private var Loading: Loading? = null

        fun show(context: Context) {
            dismiss()
            Loading = Loading(context)
            Loading?.show()
        }

        fun dismiss() {
            Loading?.dismiss()
            Loading = null
        }
    }
}