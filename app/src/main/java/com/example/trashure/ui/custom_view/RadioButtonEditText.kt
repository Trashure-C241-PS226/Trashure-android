package com.example.trashure.ui.custom_view

import android.app.AlertDialog
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

class RadioButtonEditText : AppCompatEditText, View.OnClickListener {

    private var options: Array<String> = arrayOf(
        "Apple",
        "Asus",
        "Blackberry",
        "CAT",
        "Google",
        "Huawei",
        "LG",
        "Motorola",
        "Nokia",
        "OnePlus",
        "Oppo",
        "Realme",
        "Samsung",
        "Sony",
        "Vivo",
        "Xiaomi"
    )

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        isFocusable = false
        isClickable = true
        setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (options.isNotEmpty()) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Choose an option")

            builder.setSingleChoiceItems(options, -1) { dialog, which ->
                setText(options[which])
                dialog.dismiss()
            }

            builder.show()
        }
    }
}