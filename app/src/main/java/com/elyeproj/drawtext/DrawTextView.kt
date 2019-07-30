package com.elyeproj.drawtext

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.lang.Math.abs

class DrawTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        private const val TEXT = "Sample Text"
    }

    private var drawText = TEXT
    private val drawTextCoordinate = Coordinate()

    var customText: String = ""
        set(value) {
            field = value
            invalidate()
        }

    var drawBox: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    // This is to set to ensure the coordinate Y position is fix to the
    // Sample Text height, so that it doesn't move despite of the drawText height change
    // This make it prettier when perform drawing, so the text stay on the baseline regardless
    // of the drawText change height.
    var fixHeightCoordinate: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    var fontAntialias: Boolean = true
        set(value) {
            field = value
            projectResources.paintText.isAntiAlias = field
            invalidate()
        }

    var fontScale: Float = 1.0f
        set(value) {
            field = value
            projectResources.paintText.textScaleX = field
            invalidate()
        }

    var fontSize: Int = 30
        set(value) {
            field = value
            projectResources.paintText.textSize = resources.dpToPx(field)
            invalidate()
        }

    var customCenter: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    var customAlign: Paint.Align = Paint.Align.CENTER
        set(value) {
            field = value
            projectResources.paintText.textAlign = field
            invalidate()
        }

    var customStyle: Paint.Style = Paint.Style.FILL
        set(value) {
            field = value
            projectResources.paintText.style = field
            invalidate()
        }

    private val sampleTextBound by lazy {
        val textBound = Rect()
        projectResources.paintText.getTextBounds(TEXT, 0, TEXT.length, textBound)
        textBound
    }

    private var originTextBound = calculateOriginTextBound()
    private var translatedTextBound = calculateDrawTextBound()

    private fun calculateOriginTextBound(): Rect {
        val textBound = Rect()
        projectResources.paintText.getTextBounds(drawText, 0, drawText.length, textBound)
        return textBound
    }

    private fun calculateDrawTextBound(): RectF {
        val drawTextBound = RectF()
        if (customCenter) {
            drawTextBound.top = height / 2f - originTextBound.calculateCenterY()
            drawTextBound.bottom = height / 2f + originTextBound.calculateCenterY()
        } else {
            drawTextBound.top = height / 2f - originTextBound.calculateCenterY() + originTextBound.bottom
            drawTextBound.bottom = height / 2f + originTextBound.calculateCenterY() + originTextBound.bottom
        }

        when(customAlign) {
            Paint.Align.LEFT -> {
                drawTextBound.left = width / 2f + originTextBound.right
                drawTextBound.right = width / 2f + originTextBound.left
            }
            Paint.Align.CENTER -> {
                drawTextBound.left = width / 2f - originTextBound.calculateCenterX()
                drawTextBound.right = width / 2f + originTextBound.calculateCenterX()
            }
            Paint.Align.RIGHT -> {
                drawTextBound.left = width / 2f - originTextBound.right
                drawTextBound.right = width / 2f + originTextBound.left
            }
        }
        return drawTextBound
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (width == 0 || height == 0) return

        drawText = if (customText.isBlank()) TEXT else customText
        originTextBound = calculateOriginTextBound()
        translatedTextBound = calculateDrawTextBound()

        drawTextCoordinate.x = width / 2f

        if (fixHeightCoordinate) {
            if (customCenter) {
                drawTextCoordinate.y = height / 2f - sampleTextBound.exactCenterY()
            } else {
                drawTextCoordinate.y = height / 2f + sampleTextBound.calculateCenterY()
            }
        } else {
            if (customCenter) {
                drawTextCoordinate.y = height / 2f - originTextBound.exactCenterY()
            } else {
                drawTextCoordinate.y = height / 2f + originTextBound.calculateCenterY()
            }

        }

        canvas.drawText(drawText, drawTextCoordinate.x, drawTextCoordinate.y, projectResources.paintText)
        canvas.drawPoint(drawTextCoordinate.x, drawTextCoordinate.y, projectResources.paintRed)
        if (drawBox) {
            canvas.drawRect(translatedTextBound, projectResources.paintBox)
        }


        //////////////////////////////////////////////////////////////////////////////
        // Fix the draw line per the full height of the text using the Sample Text  //
        //////////////////////////////////////////////////////////////////////////////
        canvas.drawLine(0f, 0f, width.toFloat(), 0f, projectResources.paintLight)
        canvas.drawLine(0f, height / 2f, width.toFloat(), height / 2f, projectResources.paintLight)
        canvas.drawLine(
            0f, height / 2f - sampleTextBound.calculateCenterY(),
            width.toFloat(),
            height / 2f - sampleTextBound.calculateCenterY(),
            projectResources.paintLight
        )
        canvas.drawLine(
            0f,
            height / 2f + sampleTextBound.calculateCenterY(),
            width.toFloat(),
            height / 2f + sampleTextBound.calculateCenterY(),
            projectResources.paintLight
        )
        canvas.drawLine(0f, height.toFloat(), width.toFloat(), height.toFloat(), projectResources.paintLight)
    }

    private fun Rect.calculateCenterY(): Float {
        return abs((bottom - top) / 2f)
    }

    private fun Rect.calculateCenterX(): Float {
        return abs((right - left) / 2f)
    }

    class Coordinate(var x: Float = 0f, var y: Float = 0f)
}
