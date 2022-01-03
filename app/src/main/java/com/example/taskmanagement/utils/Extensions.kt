package com.example.taskmanagement.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

 fun View.shortSnackBar(message: String, action: (Snackbar.() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    action?.let { snackBar.it() }
     snackBar.show()
}

internal fun View.longSnackBar(message: String, action: (Snackbar.() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let { snackBar.it() }
    snackBar.show()
}