package com.lohanna.catalogodefilmes.ui.common.util

import android.text.SpannableString
import android.text.TextPaint
import android.text.style.URLSpan
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView

object ViewUtils {

    fun ProgressBar.show() {
        this.visibility = View.VISIBLE
    }

    fun ProgressBar.dismiss() {
        this.visibility = View.GONE
    }

    fun TextView.removeLinksUnderline() {
        val spannable = SpannableString(text)
        for (urlSpan in spannable.getSpans(0, spannable.length, URLSpan::class.java)) {
            spannable.setSpan(object : URLSpan(urlSpan.url) {
                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                }
            }, spannable.getSpanStart(urlSpan), spannable.getSpanEnd(urlSpan), 0)
        }
        text = spannable
    }
}