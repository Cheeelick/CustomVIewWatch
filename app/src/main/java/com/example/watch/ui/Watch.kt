package com.example.watch.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.*


private const val TAG = "Watch"


class Watch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val numberSize = 50f
    var numbers: MutableList<Int> = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    val paintCircle = Paint()
    val rect = Rect()


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        drawCircle(canvas)
        drawCenter(canvas)
        drawNumber(canvas)
        drawHands(canvas)
        postInvalidate(500, 500, 500, 500)
        invalidate()
    }


    private fun drawCircle(canvas: Canvas){
        paintCircle.apply {
            reset()
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = Color.GRAY
            strokeWidth = 10f
        }
        canvas.drawCircle(width.toFloat() / 2, height.toFloat() / 2, width.toFloat() / 2, paintCircle)
    }


    private fun drawCenter(canvas: Canvas){
        paintCircle.apply {
            style = Paint.Style.FILL
            color = Color.GRAY
        }
        canvas.drawCircle(width.toFloat() / 2 , height.toFloat() / 2, 10f, paintCircle)
    }

    private fun drawNumber(canvas: Canvas){
        paintCircle.apply {
            textSize = numberSize
            color = Color.GRAY
        }

        for(i in numbers){
            paintCircle.getTextBounds(i.toString(), 0, i.toString().length, rect)
            val angle = Math.PI / 6 * (i - 3)
            val x = (width / 2 + Math.cos(angle) * (width.toFloat() / 2 - 50) - rect.width() / 2).toFloat()
            val y = (height / 2 + Math.sin(angle) * (width.toFloat() / 2 - 50) + rect.height() / 2).toFloat()
            canvas.drawText(i.toString(), x, y, paintCircle)
        }
    }


    private fun drawHands(canvas: Canvas){
        var time = Calendar.getInstance()
        var hour = time.get(Calendar.HOUR)
        hour = if (hour > 12){
            hour - 12
        } else{
            hour
        }
        drawHand(canvas, ((hour + time.get(Calendar.MINUTE) / 60.0 ) * 5f).toFloat(), "h")
        drawHand(canvas, time.get(Calendar.MINUTE).toFloat(), "m")
        drawHand(canvas, time.get(Calendar.SECOND).toFloat(), "")
    }


    private fun drawHand(canvas: Canvas, loc: Float, isHour: String){
        val angle = Math.PI * loc / 30 - Math.PI / 2
        val handRadius = if (isHour == "h"){
            (width.toFloat() / 2) - ((width.toFloat() / 2) / 2)
        }else if(isHour == "m"){
            (width.toFloat() / 2 - 100) - ((width.toFloat() / 2) / 4)
        }else{
            (width.toFloat() / 2 - 100) - ((width.toFloat() / 2) / 10)

        }

        canvas.drawLine((width / 2).toFloat(), (height / 2).toFloat(),
            (width / 2 + Math.cos(angle) * handRadius).toFloat(),
            (height / 2 + Math.sin(angle) * handRadius).toFloat(), paintCircle)
    }

}