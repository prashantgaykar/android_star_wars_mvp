package io.prashant.starwars.ui.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import io.prashant.starwars.R


class LabelTextView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {


    companion object {
        private const val TAG = "LabelTextView"
    }

    private val tvTitle: TextView
    private val tvText: TextView
    private val div: View

    var text: String? = null
        set(value) {
            tvText.text = value
            field = value
        }

    var title: String? = null
        set(value) {
            tvTitle.text = value
            field = value
        }

    init {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.LabelTextView, 0, 0
        )
        val titleDefault = a.getString(R.styleable.LabelTextView_title).toString()
        val valueDefault = a.getString(R.styleable.LabelTextView_value).toString()
        a.recycle()

        orientation = VERTICAL

        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_label_text_view, this, true)

        tvTitle = getChildAt(0) as TextView
        tvText = getChildAt(1) as TextView
        div = getChildAt(2) as View

        tvTitle.text = titleDefault
        tvText.text = valueDefault
    }

}