package com.yjh.myview.correction

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

/**
 *
 * @author jieyu.chen
 * @date 2018/10/13
 */
class MyClickableSpan(start: Int, end: Int) : ClickableSpan() {

    var mStart = start
    var mEnd = end

    lateinit var onClickListener: (v: View, str: String, x: Int, y: Int) -> Unit
    var contentStr = ""
    var x = 0
    var y = 0

    constructor(start: Int, end: Int, onClickListener: (v: View, str: String, x: Int, y: Int) -> Unit) : this(start, end) {
        this.onClickListener = onClickListener
    }


    override fun updateDrawState(ds: TextPaint) {
        ds.color = Color.RED
    }

    override fun onClick(widget: View) {
        if (!::onClickListener.isInitialized) {
            return
        }
        widget.let {
            onClickListener(widget, contentStr, x, y)
        }
    }
}