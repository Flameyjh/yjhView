package com.yjh.myview

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.View

class PieChart(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var mShowText: Boolean
    private val textPos: Int
    private val textPaint: Paint
    private val piePaint: Paint
    private val shadowPaint: Paint

    //---------------------自定义的参数-----------------------------
    private var shadowBounds:RectF
    private val mText = "test"
    private val textColor: Int = Color.BLUE
    private var textHeight: Float = 50f //文本的大小
    private var textWidth: Float = 0f //文本的宽度
    private var textX = 50f //文本的起始位置
    private var textY = 50f


    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PieChart,
            0, 0).apply {

            try {
                mShowText = getBoolean(R.styleable.PieChart_showText, false)
                textPos = getInteger(R.styleable.PieChart_labelPosition, 0)
            } finally {
                recycle()
            }
        }

        textPaint = Paint(ANTI_ALIAS_FLAG).apply {
            color = textColor
            if (textHeight == 0f) {
                textHeight = textSize
            } else {
                textSize = textHeight
            }
        }

        piePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.RED
            style = Paint.Style.FILL
            textSize = textHeight
        }

        shadowPaint = Paint(0).apply {
            color = Color.GRAY
            maskFilter = BlurMaskFilter(8f, BlurMaskFilter.Blur.NORMAL)
        }

        //-------------------自定义参数的取值----------------
        //决定椭圆的大小和形状：假想有个矩形刚好把圆包住，那么矩形的左上角坐标就是(x,y),矩形的右下角的坐标就是(x+w,y+h)
        shadowBounds = RectF(50f,50f,250f,150f)
    }

    fun isShowText(): Boolean {
        return mShowText
    }

    fun setShowText(showText: Boolean) {
        mShowText = showText
        invalidate()
        requestLayout()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // Account for padding
        var xpad = (paddingLeft + paddingRight).toFloat()
        val ypad = (paddingTop + paddingBottom).toFloat()

        // Account for the label
        if (mShowText) xpad += textWidth

        val ww = w.toFloat() - xpad
        val hh = h.toFloat() - ypad

        // Figure out how big we can make the pie.
        val diameter = Math.min(ww, hh)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Try for a width based on our minimum
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = View.resolveSizeAndState(minw, widthMeasureSpec, 1)

        // Whatever the width ends up being, ask for a height that would let the pie
        // get as big as it can
        val minh: Int = View.MeasureSpec.getSize(w) - textWidth.toInt() + paddingBottom + paddingTop
        val h: Int = View.resolveSizeAndState(
            View.MeasureSpec.getSize(w) - textWidth.toInt(),
            heightMeasureSpec,
            0
        )

        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.apply {
            // Draw the shadow
            drawOval(shadowBounds, shadowPaint)

            // Draw the label text
            drawText(mText, textX, textY, textPaint)

            // Draw the pie slices
//            mText.apply {
//                piePaint.shader = it.mShader
//                drawArc(bounds,
//                    360 - it.endAngle,
//                    it.endAngle - it.startAngle,
//                    true, piePaint)
//            }

            // Draw the pointer
            textWidth = textPaint.measureText(mText)
            drawLine(textX, textY, textX + textWidth, textY, textPaint)
        }
    }


}