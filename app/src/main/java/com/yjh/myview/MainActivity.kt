package com.yjh.myview

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Rect
import android.graphics.Shader.TileMode.MIRROR
import android.graphics.drawable.GradientDrawable
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val mStartColor = Color.parseColor("#FF0000")
    private val mEndColor = Color.parseColor("#FFFF00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //渐变文字
        gradientText()

        //渐变Button
        gradientButton()
    }

    //渐变Button
    private fun gradientButton() {
        //原本的渐变，写在shape中
        val btRectangle: Button = findViewById(R.id.bt_rectangle)

        //在使用shape的同时，用代码修改shape的颜色属性
        val gradientDrawable = btRectangle.background as GradientDrawable
        gradientDrawable.colors = intArrayOf(mStartColor, mEndColor)
    }


    //渐变文字
    private fun gradientText() {
        val tvHello: TextView = findViewById(R.id.tv_hello)

        //获取TextView中text的宽高
        val rectHello = Rect()
        val paint = tvHello.paint.apply {
            if (VERSION.SDK_INT >= VERSION_CODES.Q) {
                getTextBounds(tvHello.text, 0, tvHello.length(), rectHello)
            }
        }
        //LinearGradient 更多用法参考：https://blog.csdn.net/sqf251877543/article/details/88058363
        paint.shader = LinearGradient(0f, 0f, rectHello.right.toFloat(), 0f, mStartColor, mEndColor, MIRROR)
    }
}