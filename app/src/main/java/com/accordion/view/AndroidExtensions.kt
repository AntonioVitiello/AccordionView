package com.accordion.view

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.view.MotionEvent
import android.view.View
import android.widget.TextView

/**
 * Created by Antonio Vitiello on 17/03/2022.
 */

@Suppress("DEPRECATION")
fun TextView.html(html: CharSequence) {
    text = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        Html.fromHtml(html.toString())
    } else {
        Html.fromHtml(html.toString(), Html.FROM_HTML_MODE_COMPACT)
    }
}

@SuppressLint("ClickableViewAccessibility")
private fun View.setOnClickListener2(callback: (View) -> Unit) {
    setOnTouchListener { view, event ->
        if (event.actionMasked == MotionEvent.ACTION_UP) {
            callback.invoke(view)
            callOnClick()
        }
        true
    }
}