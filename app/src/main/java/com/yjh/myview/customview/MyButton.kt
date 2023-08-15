package com.yjh.myview.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.widget.AppCompatButton

class MyButton(context: Context, attrs: AttributeSet): AppCompatButton(context, attrs) {


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        super.onKeyDown(keyCode, event)
        Log.i("onKey事件传播", "自定义按钮MyButton的onKeyDown方法被调用")
        return false
    }

    override fun setOnClickListener(l: OnClickListener?) { //只有view初始化的时候被调用一次
        super.setOnClickListener(l)
        Log.i("click事件传播", "自定义按钮MyButton的setOnClickListener方法被调用")
    }

    override fun callOnClick(): Boolean { //一直没被调用
        super.callOnClick()
        Log.i("事件传播", "自定义按钮MyButton的callOnClick方法被调用")
        return false
    }
}