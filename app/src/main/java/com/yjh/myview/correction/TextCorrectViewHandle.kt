package com.yjh.myview.correction

import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class TextCorrectViewHandle {

    var mSwitch: Boolean = false //文本纠错开关
    var mShowIndexLine = false //是否显示下划线
    var mShowIndexRect = false //是否显示错别字rect
    var mTextRect = RectF() //错别字rect
    var mErrorTextNum = 0 //错别字总数 (看算法返回) todo yjh 多个错别字最后再做吧

//    /** 设置纠错开关  */
//    fun setTextCorrectSwitch(turnOn: Boolean) {
//        mSwitch = turnOn
//    }
//
//    /** 设置错别字总数  */
//    fun setErrorTextNum(num: Int) {
//        mErrorTextNum = num
//    }
//
//    /** 设置对应Index的下划线的位置  */
//    fun setTextRectPosition(rect: RectF) {
//        mTextRect.set(rect)
//    }
//
//    /** 设置对应Index的下划线是否显示  */
//    fun showIndexLine(show: Boolean) {
//        mShowIndexLine = show
//    }
//
//    /** 设置对应Index的rect是否显示  */
//    fun showIndexRect(show: Boolean) {
//        mShowIndexRect = show
//    }

}