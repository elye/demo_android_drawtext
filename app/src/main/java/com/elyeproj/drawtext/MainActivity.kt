package com.elyeproj.drawtext

import android.graphics.Paint
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

        (radio_center.getChildAt(0) as RadioButton).isChecked = true
        radio_center.setOnCheckedChangeListener { _, checkedId ->
            draw_text_view.customCenter = (checkedId == 2)
        }

        (radio_align.getChildAt(0) as RadioButton).isChecked = true
        radio_align.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                3 -> draw_text_view.customAlign = Paint.Align.CENTER
                4 -> draw_text_view.customAlign = Paint.Align.LEFT
                5 -> draw_text_view.customAlign = Paint.Align.RIGHT
            }
        }

        (radio_style.getChildAt(0) as RadioButton).isChecked = true
        radio_style.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                6 -> draw_text_view.customStyle = Paint.Style.FILL
                7 -> draw_text_view.customStyle = Paint.Style.FILL_AND_STROKE
                8 -> draw_text_view.customStyle = Paint.Style.STROKE
            }
        }

    }
}
