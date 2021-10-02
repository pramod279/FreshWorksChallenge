package com.freshworks.challenge.utilities

import android.content.Context
import android.widget.Toast

/**
 * @Author: Pramod Selvaraj
 * @Date: 02.10.2021
 *
 * Context Extensions For Common Utilities That Require Context
 */
fun Context.shortToast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}