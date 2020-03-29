package com.leeewy.fabwithspeeddial.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout

import com.google.android.material.appbar.AppBarLayout
import com.leeewy.fabwithspeeddial.R
import kotlinx.android.synthetic.main.layout_toolbar_collapsing.view.*
import kotlin.math.abs

class CollapsingAppBarLayout : AppBarLayout {

    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        View.inflate(context, R.layout.layout_toolbar_collapsing, this)

        context.theme.obtainStyledAttributes(attrs, R.styleable.CollapsingAppBarLayout, 0, 0).let {
            try {
                title.text = it.getString(R.styleable.CollapsingAppBarLayout_appBarText) ?: ""
            } finally {
                it.recycle()
            }
        }

        addOnOffsetChangedListener(onOffsetChangedListener)
    }

    fun enableScrolling(enable: Boolean, changeExpandedState: Boolean) {
        if (changeExpandedState) {
            setExpanded(enable, false)
        }

        isActivated = enable
        ((layoutParams as CoordinatorLayout.LayoutParams).behavior as BlockableAppBarLayoutBehavior).scrollable = enable
    }

    private val onOffsetChangedListener = OnOffsetChangedListener { appBarLayout, verticalOffset ->
        val percentage = abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange

        title.scaleY = 1 + (1 - percentage) * 0.5f
        title.scaleX = 1 + (1 - percentage) * 0.5f
    }
}
