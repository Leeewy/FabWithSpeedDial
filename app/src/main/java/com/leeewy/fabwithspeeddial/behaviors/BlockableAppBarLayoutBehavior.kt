package com.leeewy.fabwithspeeddial.behaviors

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

@Suppress("unused")
class BlockableAppBarLayoutBehavior(
    context: Context,
    attrs: AttributeSet
) : AppBarLayout.Behavior(context, attrs) {

    var scrollable = true

    override fun onStartNestedScroll(parent: CoordinatorLayout, child: AppBarLayout, directTargetChild: View, target: View, nestedScrollAxes: Int, type: Int): Boolean =
        scrollable && super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type)

    override fun onTouchEvent(parent: CoordinatorLayout, child: AppBarLayout, ev: MotionEvent): Boolean =
        scrollable && super.onTouchEvent(parent, child, ev)
}