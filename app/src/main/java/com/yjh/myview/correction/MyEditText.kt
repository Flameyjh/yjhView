package com.yjh.myview.correction

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet

class MyEditText(context: Context, attrs: AttributeSet): androidx.appcompat.widget.AppCompatEditText(context, attrs) {
    private val mPaintLine = Paint().apply {
        color = Color.RED
    }
    private val mPaintRect = Paint().apply {
        color = Color.RED
        alpha = 100
    }

    private var mSwitch: Boolean = false //文本纠错开关
    private var mShowIndexLine = false //是否显示下划线
    private var mShowIndexRect = false //是否显示错别字rect
    private var mTextRect = RectF() //错别字rect
    private var mErrorTextNum = 0 //错别字总数 (看算法返回) todo yjh 多个错别字最后再做吧

    /** 设置纠错开关  */
    fun setTextCorrectSwitch(turnOn: Boolean) {
        mSwitch = turnOn
    }

    /** 设置错别字总数  */
    fun setErrorTextNum(num: Int) {
        mErrorTextNum = num
    }

    /** 设置对应Index的下划线的位置  */
    fun setTextRectPosition(rect: RectF) {
        mTextRect.set(rect)
    }

    /** 设置对应Index的下划线是否显示  */
    fun showIndexLine(show: Boolean) {
        mShowIndexLine = show
    }

    /** 设置对应Index的rect是否显示  */
    fun showIndexRect(show: Boolean) {
        mShowIndexRect = show
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //画红色下划线
        if (mShowIndexLine) {
            canvas.drawRect(
                mTextRect.left,
                mTextRect.bottom,
                mTextRect.right,
                mTextRect.bottom + 8f,
                mPaintLine
            )
        }
        //画红色rect
        if (mShowIndexRect) {
            canvas.drawRect(mTextRect, mPaintRect)
        }
    }

}