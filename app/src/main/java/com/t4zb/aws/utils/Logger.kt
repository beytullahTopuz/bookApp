package com.t4zb.aws.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun showLogError(tag: String, string: String) {
    Log.i(tag, "showLogError: $string")
}

fun showLogDebug(tag: String, string: String) {
    Log.d(tag, "showLogDebug: $string")
}

fun showToast(context: Context, string: String) {
    Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
}

fun showSnack(view: View, string: String){
    Snackbar.make(view, string, Snackbar.LENGTH_SHORT).show()
}