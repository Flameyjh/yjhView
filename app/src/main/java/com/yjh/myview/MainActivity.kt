package com.yjh.myview

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader.TileMode.MIRROR
import android.graphics.drawable.GradientDrawable
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ss.android.ugc.aweme.sticker.text.correction.TextCorrectHelper
import com.yjh.myview.correction.MyEditText
import com.yjh.myview.correction.TextCorrectViewHandle


class MainActivity : AppCompatActivity() {

    private val mStartColor = Color.parseColor("#FF0000")
    private val mEndColor = Color.parseColor("#FFFF00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //1. 自定义view 2. shape

        //3. 渐变文字
        gradientText()

        //4. 渐变Button
        gradientButton()

        //5. 输入框文字下划线和点击事件
        handleEditText()

        //5. 输入框文字下划线和点击事件 方案2
        val textView: MyEditText = findViewById(R.id.tv_underline2)
        handleEditText2(textView)
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

    //输入框文字下划线和点击事件
    private fun handleEditText() {
        val textView: TextView = findViewById(R.id.tv_underline)
        val text = textView.text
        val spannableString = SpannableStringBuilder(text)
        val underlineSpan = UnderlineSpan()
        spannableString.setSpan(underlineSpan, 1, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        textView.text = spannableString
    }

    //输入框文字下划线和点击事件 方案2
    private fun handleEditText2(textView: MyEditText) {

        //Layout要等TextView绘制完了才能够拿到Layout的对象。直接获取Layout值都是null
        Handler(Looper.getMainLooper()).postDelayed( {
            spanableTextView(textView)
        }, 100)
    }

    private fun spanableTextView(textView: MyEditText) {
        //假设后端返回
        val offsetList = listOf<Int>(2, 5, 7)

        //画红线
        textView.setTextCorrectSwitch(true)
        textView.setErrorTextNum(offsetList.size)
        textView.showIndexLine(true)
        textView.showIndexRect(true)
        for (offset in offsetList) { //利用index画UI
            val floats = TextCorrectHelper.measureXY(textView, offset)
            val rect = RectF(floats?.get(0) ?: 0f, floats?.get(1) ?: 0f, floats?.get(2) ?: 0f, floats?.get(3) ?: 0f)
            textView.setTextRectPosition(rect)
        }

        //画气泡



    }

    //参考
//        val spanableInfo = SpannableString("这是一个测试文本，点击我看看！")
//        val span = MyClickableSpan(2, 3, onClickListener)
//        val lines = mutableListOf<UnderLineOptions>()
//        val lineOptions = UnderLineOptions(span.mStart, span.mEnd, span)
//        lines.add(lineOptions)
//        TextCorrectionHelper.setTextViewLines(textView, lines)
}