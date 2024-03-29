package com.yjh.myview

import android.annotation.SuppressLint
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
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ss.android.ugc.aweme.sticker.text.correction.TextCorrectHelper
import com.yjh.myview.correction.MyEditText


class MainActivity : AppCompatActivity() {

    private val mStartColor = Color.parseColor("#FF0000")
    private val mEndColor = Color.parseColor("#FFFF00")

    @SuppressLint("ClickableViewAccessibility")
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
        handleEditText2()

        //6. Android事件处理机制: 事件传播顺序: 监听器—>view组件的回调方法—>Activity的回调方法,返回值false继续传播，true终止传播
        onHandleAction()

        //7. 响应系统设置的事件(Configuration类)

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        super.onKeyDown(keyCode, event)
        Log.i("onKey事件传播", "Activity的onKeyDown方法被调用")
        return false
    }

    //Android事件处理机制: 事件传播顺序
    @SuppressLint("ClickableViewAccessibility")
    private fun onHandleAction() {
        val btKeyDown: Button = findViewById(R.id.bt_key_down)
        btKeyDown.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event?.action == KeyEvent.ACTION_DOWN) {
                    Log.i("onKey事件传播", "监听器的onKeyDown方法被调用")
                }
                return false
            }
        })
        btKeyDown.setOnClickListener{
            Log.i("click事件传播", "监听器的setOnClickListener方法被调用")
        }
        btKeyDown.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_DOWN -> {
                        Log.i("onTouch事件传播", "监听器的ACTION_DOWN方法被调用")
                    }
                    MotionEvent.ACTION_MOVE -> {
                        Log.i("onTouch事件传播", "监听器的ACTION_MOVE方法被调用")
                    }
                    MotionEvent.ACTION_UP -> {
                        Log.i("onTouch事件传播", "监听器的ACTION_UP方法被调用")
                    }
                }
                return false //返回false不消费，继续传递；返回true消费，setOnClickListener不会被调用
            }
        })
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
    @SuppressLint("ClickableViewAccessibility")
    private fun handleEditText2() {
        val textView: MyEditText = findViewById(R.id.tv_underline2)
        textView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View, event: MotionEvent): Boolean {
                Log.i("yjhtext", "手势：触摸事件")
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_DOWN -> {
                        Log.i("yjhtext", "手势：down")
                    }
                }
                return true
            }
        })
        //Layout要等TextView绘制完了才能够拿到Layout的对象。直接获取Layout值都是null
        Handler(Looper.getMainLooper()).postDelayed( {
            textView.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
                override fun afterTextChanged(s: Editable?) {
                    spanableTextView(textView)
                    textView.invalidate()
                }
            })
        }, 100)
    }

    private fun spanableTextView(textView: MyEditText) {
        //假设后端返回
        val offsetList = listOf<Int>(2, 3, 5)
        //根据返回处理UI配置
        textView.errorTextData.switch = true
        textView.errorTextData.errorTextNum = offsetList.size
        for (index in offsetList.indices) {
            val floats = TextCorrectHelper.measureXY(textView, offsetList[index])
            val rect = RectF(floats?.get(0) ?: 0f, floats?.get(1) ?: 0f, floats?.get(2) ?: 0f, floats?.get(3) ?: 0f)
            textView.errorTextData.textRect?.add(rect)
        }
        //画气泡
        textView.invalidate()
    }

}