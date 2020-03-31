package com.leeewy.fabwithspeeddial.behaviors

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.leeewy.fabwithspeeddial.widgets.ExpandableExtendedFloatingActionButton

@Suppress("unused")
class CustomExtendedFloatingActionButtonBehavior<T : ExtendedFloatingActionButton>(
    context: Context,
    attrs: AttributeSet
) : CoordinatorLayout.Behavior<T>(context, attrs) {

    var blockCurrentState: Boolean = false

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: T, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: T, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed)

        if (blockCurrentState) {
            return
        }

        if (dyConsumed > 0 && child.isExtended) {
            if (child is ExpandableExtendedFloatingActionButton) child.shrinkAfterScroll() else child.shrink()
        } else if (dyConsumed < 0 && !child.isExtended) {
            if (child is ExpandableExtendedFloatingActionButton) child.extendAfterScroll() else child.extend()
        }
    }
}