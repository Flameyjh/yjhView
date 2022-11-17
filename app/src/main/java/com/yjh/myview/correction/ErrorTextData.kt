package com.yjh.myview.correction

import android.graphics.RectF

class ErrorTextData {
    var switch: Boolean = false //文本纠错开关
    var showIndexLine = arrayListOf(false) //是否显示下划线
    var showIndexRect = arrayListOf(false) //是否显示错别字rect
    var textRect: MutableList<RectF>? = mutableListOf() //错别字位置rect
    var errorTextNum = 0 //错别字总数 (看算法返回)
}