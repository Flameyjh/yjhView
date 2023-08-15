package com.yjh.myview.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.yjh.myview.R.styleable
import java.lang.Math.min

class CircleView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var mColor: Int = Color.RED
    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            styleable.CircleView,
            0, 0).apply {

            try {
                mColor = getColor(styleable.CircleView_circle_color, Color.RED)
            } finally {
                recycle()
            }
        }
        mPaint.color = mColor
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode: Int = MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize: Int = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode: Int = MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize: Int = MeasureSpec.getSize(heightMeasureSpec)

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, 200)
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, heightSpecSize)
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, 200)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paddingLeft = paddingLeft
        val paddingRight = paddingRight
        val paddingTop = paddingTop
        val paddingBottom = paddingBottom

        val width = width - paddingLeft - paddingRight
        val height = height - paddingTop - paddingBottom
        val radius = min(width, height) / 2
        canvas?.drawCircle(
            (paddingLeft + width / 2).toFloat(),
            (paddingTop + height / 2).toFloat(), radius.toFloat(), mPaint
        )
    }
}