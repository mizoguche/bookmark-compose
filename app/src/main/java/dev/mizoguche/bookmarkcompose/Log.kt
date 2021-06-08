package dev.mizoguche.bookmarkcompose

import android.util.Log

fun Any.logDebug(msg: String) {
    Log.d(this::class.simpleName, msg)
}