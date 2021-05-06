package com.example.newsapp.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.newsapp.R
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Aditya Patil on 05-May-21.
 * aspatil9021@gmail.com
 * 9021-93-9021
 */

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun AppCompatButton.enable() {
    isEnabled = true
}

fun AppCompatButton.disable() {
    isEnabled = false
}

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}

fun View.snackbarError(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.setBackgroundTint(ContextCompat.getColor(this.context, R.color.red))
        .show()
}