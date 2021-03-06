package com.elyeproj.drawtext

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
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

    var fontHinting: Int = Paint.HINTING_ON
        set(value) {
            field = value
            projectResources.paintText.hinting = field
            invalidate()
        }

    var fontFakeBold: Boolean = false
        set(value) {
            field = value
            projectResources.paintText.isFakeBoldText = field
            invalidate()
        }

    var fontUnderline: Boolean = false
        set(value) {
            field = value
            projectResources.paintText.isUnderlineText = field
            invalidate()
        }

    var fontStrikeThrough: Boolean = false
        set(value) {
            field = value
            projectResources.paintText.isStrikeThruText = field
            invalidate()
        }

    var fontFeatureSetting: String = ""
        set(value) {
            field = value
            projectResources.paintText.fontFeatureSettings = field
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

    var fontSkew: Float = 0f
        set(value) {
            field = value
            projectResources.paintText.textSkewX = field
            invalidate()
        }

    var letterSpacing: Float = 0f
        set(value) {
            field = value
            projectResources.paintText.letterSpacing = field
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


    var typeFace: Typeface = Typeface.DEFAULT
        set(value) {
            field = value
            projectResources.paintText.typeface = field
            invalidate()
        }

    private val sampleTextBound by lazy {
        val textBound = Rect()
        projectResources.paintText.getTextBounds(TEXT, 0, TEXT.length, textBound)
        textBound
    }

    private var originTextBound = calculateOriginTextBound()

    private fun calculateOriginTextBound(): Rect {
        val textBound = Rect()
        projectResources.paintText.getTextBounds(drawText, 0, drawText.length, textBound)
        return textBound
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (width == 0 || height == 0) return

        drawText = if (customText.isBlank()) TEXT else customText
        originTextBound = calculateOriginTextBound()

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
            var lineXbegin = drawTextCoordinate.x
            var lineXend = drawTextCoordinate.x
            val textMeasure = projectResources.paintText.measureText(drawText)
            when (customAlign) {
                Paint.Align.LEFT -> {
                    originTextBound.offset(drawTextCoordinate.x.toInt(), drawTextCoordinate.y.toInt())

                    lineXend += textMeasure
                }
                Paint.Align.CENTER -> {
                    originTextBound.offset(
                        (drawTextCoordinate.x - textMeasure/2).toInt(),
                        drawTextCoordinate.y.toInt()
                    )

                    lineXbegin -= textMeasure/2
                    lineXend += textMeasure/2
                }
                Paint.Align.RIGHT -> {
                    originTextBound.offset(
                        (drawTextCoordinate.x - textMeasure).toInt(),
                        drawTextCoordinate.y.toInt()
                    )

                    lineXbegin -= textMeasure
                }
            }
            canvas.drawRect(originTextBound, projectResources.paintBox)

            // Draw the measure line as well
            canvas.drawLine(lineXbegin, height - 20f,
                lineXend, height - 20f, projectResources.paintRedLine)
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
