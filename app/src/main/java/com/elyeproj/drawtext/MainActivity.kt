package com.elyeproj.drawtext

import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.RadioButton
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        projectResources = ProjectResources(resources)
        setContentView(R.layout.activity_main)

        text_input.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                draw_text_view.customText = text_input.text.toString()
            }
        })

        switch_box.setOnCheckedChangeListener { _, isChecked ->
            draw_text_view.drawBox = isChecked
        }

        switch_fix_height_coordinate.setOnCheckedChangeListener { _, isChecked ->
            draw_text_view.fixHeightCoordinate = isChecked
        }

        switch_antialias.setOnCheckedChangeListener { _, isChecked ->
            draw_text_view.fontAntialias = isChecked
        }

        switch_hinting.setOnCheckedChangeListener { _, isChecked ->
            draw_text_view.fontHinting = if (isChecked) Paint.HINTING_ON else Paint.HINTING_OFF
        }

        switch_fake_bold.setOnCheckedChangeListener { _, isChecked ->
            draw_text_view.fontFakeBold = isChecked
        }

        switch_underline.setOnCheckedChangeListener { _, isChecked ->
            draw_text_view.fontUnderline = isChecked
        }

        switch_strike_through.setOnCheckedChangeListener { _, isChecked ->
            draw_text_view.fontStrikeThrough = isChecked
        }

        switch_font_feature.setOnCheckedChangeListener { _, isChecked ->
            draw_text_view.fontFeatureSetting = if (isChecked) "smcp" else ""
        }

        seek_scale.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                draw_text_view.fontScale = progress/10f
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seek_size.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                draw_text_view.fontSize = progress
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seek_skew.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                draw_text_view.fontSkew = -progress/10f
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seek_space.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                draw_text_view.letterSpacing = progress/100f
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        (radio_center.getChildAt(0) as RadioButton).isChecked = true
        radio_center.setOnCheckedChangeListener { _, checkedId ->
            when (findViewById<RadioButton>(checkedId).id) {
                R.id.radio_calculate -> draw_text_view.customCenter = false
                R.id.radio_exact -> draw_text_view.customCenter = true
            }
        }

        (radio_align.getChildAt(0) as RadioButton).isChecked = true
        radio_align.setOnCheckedChangeListener { _, checkedId ->
            when (findViewById<RadioButton>(checkedId).id) {
                R.id.radio_align_center -> draw_text_view.customAlign = Paint.Align.CENTER
                R.id.radio_align_left -> draw_text_view.customAlign = Paint.Align.LEFT
                R.id.radio_align_right -> draw_text_view.customAlign = Paint.Align.RIGHT
            }
        }

        (radio_style.getChildAt(0) as RadioButton).isChecked = true
        radio_style.setOnCheckedChangeListener { _, checkedId ->
            when (findViewById<RadioButton>(checkedId).id) {
                R.id.radio_style_fill -> draw_text_view.customStyle = Paint.Style.FILL
                R.id.radio_style_fill_stroke -> draw_text_view.customStyle = Paint.Style.FILL_AND_STROKE
                R.id.radio_style_stroke -> draw_text_view.customStyle = Paint.Style.STROKE
            }
        }

        (radio_typeface.getChildAt(0) as RadioButton).isChecked = true
        radio_typeface.setOnCheckedChangeListener { _, checkedId ->
            when (findViewById<RadioButton>(checkedId).id) {
                R.id.radio_typeface_default -> draw_text_view.typeFace = Typeface.DEFAULT
                R.id.radio_typeface_default_bold -> draw_text_view.typeFace = Typeface.DEFAULT_BOLD
                R.id.radio_typeface_san_serif -> draw_text_view.typeFace = Typeface.SANS_SERIF
                R.id.radio_typeface_serif -> draw_text_view.typeFace = Typeface.SERIF
                R.id.radio_typeface_monospace -> draw_text_view.typeFace = Typeface.MONOSPACE
                R.id.radio_typeface_custom_waltz_disney -> draw_text_view.typeFace =
                    Typeface.createFromAsset(assets, "waltograph42.ttf")
            }
        }
    }
}
