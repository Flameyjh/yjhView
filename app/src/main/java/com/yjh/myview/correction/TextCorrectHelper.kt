package com.ss.android.ugc.aweme.sticker.text.correction

import android.graphics.Rect
import android.widget.TextView
import com.yjh.myview.correction.UnderLineOptions

object TextCorrectHelper {

    /** 计算textview中某一个字符的坐标, 返回左上右下  */
    fun measureXY(textView: TextView, poi: Int): FloatArray? {
        val floats = FloatArray(4)
        val layout = textView.layout
        val line: Int = layout.getLineForOffset(poi) //得到所在行数
        val rect = Rect()
        layout.getLineBounds(line, rect) //得到这一行的外包矩形
        //左
        floats[0] = layout.getPrimaryHorizontal(poi) + textView.paddingLeft //字符左边x坐标
        //上
        floats[1] = (rect.top + textView.paddingTop).toFloat() //字符顶部y坐标
        //右
        floats[2] = layout.getSecondaryHorizontal(poi) + textView.paddingLeft //字符右边x坐标
        //下
        floats[3] = (rect.bottom + textView.paddingTop).toFloat() //字符底部y坐标
        //原因不明，获取到的坐标左右相同，暂时加上字符宽度应对
        if (floats[0] == floats[2]) {
            val s = textView.text.toString().substring(poi, poi + 1);//当前字符
            floats[2] = floats[2] + textView.paint.measureText(s);//加上字符宽度
        }
        return floats
    }









    //-------------------------暂时没用到----------------------------------------

//    fun setTextViewLines(textView: TextView, lines: List<UnderLineOptions>, ) {
//        textView.post(Runnable {
//            for (option in lines) {
//                if (!addOptions(textView, option)) {
//                    break
//                }
//            }
//            textView.invalidate()
//        })
//    }

    /**
     * 将配置内容添加到列表中
     *
     * @param option
     * @return
     */
//    private fun addOptions(textView: TextView, option: UnderLineOptions): Boolean {
//        val s: Int = option.lineStart
//        val e: Int = option.lineEnd
//        if (s > textView.getText().toString().length) {
//            return false
//        }
//        if (e < 0) {
//            return false
//        }
//        val start = if (s < 0) 0 else s
//        val end = if (e > textView.getText().toString().length) textView.getText().toString().length else e
//        option.contentStr = textView.getText().toString().substring(start, end)
//        if (option.myClickableSpan != null) {
//            option.myClickableSpan!!.mStart = start
//            option.myClickableSpan!!.mEnd = end
//            option.myClickableSpan!!.contentStr = textView.getText().toString().subSequence(start, end).toString()
//        }
//        // 可以通过这种方法获取被这一部分是否可以被点击
////        ClickableSpan[] links = ((Spannable) getText()).getSpans(start,
////                end, ClickableSpan.class);
////        System.out.println(getSelectionStart());
////        System.out.println(getSelectionEnd());
////        System.out.println(links.length > 0 ? links[0] : links);
//        val startXY: FloatArray? = measureXY(textView, start)
//        val endXY: FloatArray? = measureXY(textView, end)
//        val listXY: MutableList<FloatArray> = ArrayList()
//        if (startXY == null || endXY == null || startXY.isEmpty() || endXY.isEmpty()) {
//            return false
//        }
//        if (startXY[1] == endXY[1]) {
//            listXY.add(startXY)
//            listXY.add(endXY)
//            //找到弹出框的中间点
//            if (option.myClickableSpan != null) {
//                val x = (startXY[0] + (endXY[0] - startXY[0]) / 2).toInt()
//                option.myClickableSpan!!.x = x
//                option.myClickableSpan!!.y = endXY[1].toInt()
//            }
//            option.linesXY = listXY
//        } else {
//            // 这里处理折行的情况
//            // 对于折行的弹窗，只能根据需求来做了。
//            val lineStart: Int = textView.getLayout().getLineForOffset(start)
//            val lineEnd: Int = textView.getLayout().getLineForOffset(end)
//            var lineNum = lineStart
//            while (lineNum <= lineEnd) {
//                val rect1 = Rect()
//                textView.getLayout().getLineBounds(lineNum, rect1)
//                println(rect1)
//                println(textView.getLayout().getLineMax(lineNum))
//                if (lineNum == lineStart) {
//                    val endXYN = floatArrayOf(
//                        textView.getLayout().getLineMax(lineNum) + (measureXY(textView, textView.getLayout().getLineStart(lineNum))?.get(0) ?: 0f),
//                        startXY[0],
//                        textView.getLayout().getLineMax(lineNum) + (measureXY(textView, textView.getLayout().getLineStart(lineNum))?.get(0) ?: 0f),
//                        startXY[3]
//                    )
//                    listXY.add(startXY)
//                    listXY.add(endXYN)
//                } else if (lineNum == lineEnd) {
//                    val startXYN = floatArrayOf(
//                        measureXY(textView, textView.getLayout().getLineStart(lineNum))?.get(0) ?: 0f,
//                        endXY[0],
//                        measureXY(textView, textView.getLayout().getLineStart(lineNum))?.get(0) ?: 0f,
//                        endXY[3]
//                    )
//                    listXY.add(startXYN)
//                    listXY.add(endXY)
//                } else {
//                    val rect = Rect()
//                    textView.getLayout().getLineBounds(lineNum, rect)
//                    val startXYN = floatArrayOf(
//                        measureXY(textView, textView.getLayout().getLineStart(lineNum))?.get(0) ?:0f, (
//                                rect.top + textView.getPaddingTop()).toFloat(),
//                        measureXY(textView, textView.getLayout().getLineStart(lineNum))?.get(0) ?:0f, (
//                                rect.bottom + textView.getPaddingTop()).toFloat()
//                    )
//                    val endXYN = floatArrayOf(
//                        textView.getLayout().getLineMax(lineNum) + (measureXY(textView, textView.getLayout().getLineStart(lineNum))?.get(0) ?: 0f), (
//                                rect.top + textView.getPaddingTop()).toFloat(),
//                        textView.getLayout().getLineMax(lineNum) + (measureXY(textView, textView.getLayout().getLineStart(lineNum))?.get(0) ?:0f), (
//                                rect.bottom + textView.getPaddingTop()).toFloat()
//                    )
//                    listXY.add(startXYN)
//                    listXY.add(endXYN)
//                }
//                lineNum++
//            }
//            option.linesXY = listXY
//        }
////        lineOptions.add(option) //todo yjh
//        return true
//    }
}
