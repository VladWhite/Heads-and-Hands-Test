package ru.belenkov.headsandhandstest.util

import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import ru.belenkov.headsandhandstest.R

fun createSnackBar(
    view: View,
    text: String = "snackbar text",
    actionName: String = view.context.getString(R.string.snackbar_action),
    actionListener: View.OnClickListener? = null
): Snackbar {
    val snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
    if (actionListener == null) {
        snackbar.setAction(actionName) { snackbar.dismiss() }
    } else {
        snackbar.setAction(actionName, actionListener)
    }
    val text = snackbar.view.findViewById<TextView>(R.id.snackbar_text)
    text.maxLines = 10

    return snackbar
}