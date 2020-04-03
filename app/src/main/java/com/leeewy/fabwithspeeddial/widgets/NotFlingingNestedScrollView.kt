package com.leeewy.fabwithspeeddial.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView

class NotFlingingNestedScrollView(
    context: Context,
    attrs: AttributeSet?
) : NestedScrollView(context, attrs) {

    override fun fling(velocityY: Int) {
        // workaround for clicks while flinging
    }
}