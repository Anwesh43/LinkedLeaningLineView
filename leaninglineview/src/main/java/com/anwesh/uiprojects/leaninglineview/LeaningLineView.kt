package com.anwesh.uiprojects.leaninglineview

/**
 * Created by anweshmishra on 25/12/19.
 */

import android.view.View
import android.view.MotionEvent
import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color

val nodes : Int = 5
val rotation : Float = 45f
val offset : Float = 0.8f
val scGap : Float = 0.5f
val lines : Int = 6
val strokeFactor : Int = 90
val foreColor : Int = Color.parseColor("#3F51B5")
val backColor : Int = Color.parseColor("#BDBDBD")
val delay : Long = 30
val sizeFactor : Float = 2.9f

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()
fun Float.cosify() : Float = 1f - Math.sin(Math.PI / 2 + (Math.PI / 2) * this).toFloat()

fun Canvas.drawLeaningLine(i : Int, scale : Float, gap : Float, size : Float, paint : Paint) {
    val sf : Float = scale.sinify().divideScale(i, lines)
    save()
    translate(i * gap, 0f)
    rotate(rotation * sf)
    drawLine(0f, 0f, 0f, -size, paint)
    restore()
}

fun Canvas.drawLeaningLines(scale : Float, w : Float, size : Float, paint : Paint) {
    val sc : Float = scale.divideScale(1, 2).cosify()
    val offsetW : Float = w * offset
    val start : Float = (w - offsetW) / 2
    val gap : Float = offsetW / lines
    save()
    translate(start, 0f)
    for (j in 0..(lines - 1)) {
        drawLeaningLine(j, scale, gap, size, paint)
    }
    drawLine(offsetW * (1 - sc), 0f, offsetW, 0f, paint)
    restore()
}

fun Canvas.drawLLNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    val gap : Float = h / (nodes + 1)
    val size = gap / sizeFactor
    save()
    translate(0f, gap * (i + 1))
    drawLeaningLines(scale, w, size, paint)
    restore()
}

class LeaningLineView(ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var prevScale : Float = 0f, var dir : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            scale += scGap * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }
}