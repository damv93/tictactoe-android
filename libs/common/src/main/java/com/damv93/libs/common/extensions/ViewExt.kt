package com.damv93.libs.common.extensions

import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible

fun View.visible() {
    isVisible = true
}

fun View.gone() {
    isGone = true
}