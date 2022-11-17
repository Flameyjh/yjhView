package com.yjh.myview.correction

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log

class MyEditText(context: Context, attrs: AttributeSet): androidx.appcompat.widget.AppCompatEditText(context, attrs) {
    private val mPaintLine = Paint().apply {
        color = Color.RED
    }
    private val mPaintRect = Paint().apply {
        color = Color.RED
        alpha = 100
    }
    var errorTextData = ErrorTextData()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.i("yjh", "画画啦")
        //根据配置绘制UI
        if (errorTextData.switch) {
            //画红色下划线
            for (index in 0 until errorTextData.errorTextNum) {
                if (errorTextData.textRect?.get(index) != null) {
                    canvas.drawRect(
                        errorTextData.textRect!![index].left,
                        errorTextData.textRect!![index].bottom,
                        errorTextData.textRect!![index].right,
                        errorTextData.textRect!![index].bottom + 8f,
                        mPaintLine
                    )
                }
                //画红色rect
                if (errorTextData.textRect?.get(index) != null) {
                    canvas.drawRect(errorTextData.textRect!![index], mPaintRect)
                }
                Log.i("yjh", "画红线 index=${index}")
            }
        }
    }

}