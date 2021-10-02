package com.freshworks.challenge.utilities

import android.view.HapticFeedbackConstants
import android.view.View

/**
 * @Author: Pramod Selvaraj
 * @Date: 02.10.2021
 *
 * Utility Class For Managing App Utils
 */

/**
 * Vibrate the device when user performs any click action
 */
fun vibrate(view: View) {
    view.performHapticFeedback(
        HapticFeedbackConstants.VIRTUAL_KEY,
        HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
    )
}