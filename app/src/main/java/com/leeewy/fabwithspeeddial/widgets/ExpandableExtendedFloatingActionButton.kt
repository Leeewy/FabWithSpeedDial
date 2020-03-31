package com.leeewy.fabwithspeeddial.widgets

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.google.android.material.expandable.ExpandableTransformationWidget
import com.google.android.material.expandable.ExpandableWidgetHelper
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.stateful.ExtendableSavedState
import com.leeewy.fabwithspeeddial.R
import com.leeewy.fabwithspeeddial.behaviors.CustomExtendedFloatingActionButtonBehavior

class ExpandableExtendedFloatingActionButton : ExtendedFloatingActionButton, ExpandableTransformationWidget {

    private var expandableWidgetHelper: ExpandableWidgetHelper = ExpandableWidgetHelper(this)

    private var wasExtended = true

    private var autoAnimation: Boolean = false

    private var animationDuration: Int = 0

    @DrawableRes
    private var expandingDrawable: Int = 0

    @DrawableRes
    private var collapsingDrawable: Int = 0

    @ColorInt
    private var expandedIconColor: Int = 0

    @ColorInt
    private var collapsedIconColor: Int = 0

    @ColorInt
    private var expandedBackgroundColor: Int = 0

    @ColorInt
    private var collapsedBackgroundColor: Int = 0

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
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.ExpandableExtendedFloatingActionButton, 0, 0)

        try {
            autoAnimation = a.getBoolean(R.styleable.ExpandableExtendedFloatingActionButton_autoAnimation, false)
            animationDuration = a.getInteger(R.styleable.ExpandableFloatingActionButton_animationDuration, 500)
            expandingDrawable = a.getResourceId(R.styleable.ExpandableExtendedFloatingActionButton_expandingDrawable, 0)
            collapsingDrawable = a.getResourceId(R.styleable.ExpandableExtendedFloatingActionButton_collapsingDrawable, 0)
            expandedIconColor = a.getColor(R.styleable.ExpandableExtendedFloatingActionButton_expandedIconColor, 0)
            collapsedIconColor = a.getColor(R.styleable.ExpandableExtendedFloatingActionButton_collapsedIconColor, 0)
            expandedBackgroundColor = a.getColor(R.styleable.ExpandableExtendedFloatingActionButton_expandedBackgroundColor, 0)
            collapsedBackgroundColor = a.getColor(R.styleable.ExpandableExtendedFloatingActionButton_collapsedBackgroundColor, 0)
        } finally {
            a.recycle()
        }
    }

    override fun setExpanded(expanded: Boolean): Boolean {
        return expandableWidgetHelper.setExpanded(expanded).also {
            animateExpandingEndCollapsing()
        }
    }

    override fun isExpanded(): Boolean = expandableWidgetHelper.isExpanded

    override fun setExpandedComponentIdHint(@IdRes expandedComponentIdHint: Int) {
        expandableWidgetHelper.expandedComponentIdHint = expandedComponentIdHint
    }

    override fun getExpandedComponentIdHint(): Int = expandableWidgetHelper.expandedComponentIdHint

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState() ?: Bundle()

        return ExtendableSavedState(superState).apply {
            extendableStates.put(EXPANDABLE_WIDGET_HELPER_KEY, expandableWidgetHelper.onSaveInstanceState())

            extendableStates.put(EXPANDABLE_EXTENDED_FLOATING_ACTION_BUTTON_KEY, Bundle().apply {
                putBoolean(WAS_EXTENDED_KEY, wasExtended)
                putBoolean(IS_EXTENDED_KEY, isExtended)
            })
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state !is ExtendableSavedState) {
            super.onRestoreInstanceState(state)
        } else {
            super.onRestoreInstanceState(state.superState)

            state.extendableStates[EXPANDABLE_WIDGET_HELPER_KEY]?.let {
                expandableWidgetHelper.onRestoreInstanceState(it)
            }

            state.extendableStates[EXPANDABLE_EXTENDED_FLOATING_ACTION_BUTTON_KEY]?.let {
                wasExtended = it.getBoolean(WAS_EXTENDED_KEY)
                isExtended = it.getBoolean(IS_EXTENDED_KEY)
            }

            animateExpandingEndCollapsing()
        }
    }

    fun shrinkAfterScroll() {
        shrink()

        wasExtended = false
    }

    fun extendAfterScroll() {
        extend()

        wasExtended = true
    }

    private fun animateExpandingEndCollapsing() {
        if (!autoAnimation) {
            if (wasExtended) {
                extend()
            } else {
                shrink()
            }

            return
        }

        animateIconDrawable()
        animateExtendetion()

        mutableListOf<Animator>().apply {
            getIconColorAnimator()?.let {
                add(it)
            }

            getBackgroundColorAnimator()?.let {
                add(it)
            }
        }.let {
            AnimatorSet().apply {
                playTogether(it)
            }.start()
        }
    }

    private fun animateIconDrawable() {
        if (expandingDrawable != 0 && collapsingDrawable != 0) {
            (ContextCompat.getDrawable(
                context,
                if (isExpanded) expandingDrawable else collapsingDrawable
            ).also {
                icon = it
            } as AnimatedVectorDrawable).start()
        }
    }

    private fun getIconColorAnimator(): Animator? {
        return if (expandedIconColor != 0 && collapsedIconColor != 0) {
            createColorAnimator(expandedIconColor, collapsedIconColor).apply {
                duration = animationDuration.toLong()
                addUpdateListener { animator ->
                    iconTint = ColorStateList.valueOf(animator.animatedValue as Int)
                }
            }
        } else {
            null
        }
    }

    private fun getBackgroundColorAnimator(): Animator? {
        return if (expandedBackgroundColor != 0 && collapsedBackgroundColor != 0) {
            createColorAnimator(expandedBackgroundColor, collapsedBackgroundColor).apply {
                duration = animationDuration.toLong()
                addUpdateListener { animator ->
                    backgroundTintList = ColorStateList.valueOf(animator.animatedValue as Int)
                }
            }
        } else {
            null
        }
    }

    private fun animateExtendetion() {
        if (isExpanded) {
            shrink()

            blockCurrentState(true)
        } else {
            if (wasExtended) {
                extend()
            }

            blockCurrentState(false)
        }
    }

    private fun createColorAnimator(@ColorInt expandedColorId: Int, @ColorInt collapsedColorId: Int): ValueAnimator {
        return ValueAnimator.ofObject(
            ArgbEvaluator(),
            if (isExpanded) expandedColorId else collapsedColorId,
            if (isExpanded) collapsedColorId else expandedColorId
        )
    }

    private fun blockCurrentState(block: Boolean) {
        ((layoutParams as CoordinatorLayout.LayoutParams).behavior as CustomExtendedFloatingActionButtonBehavior).blockCurrentState = block
    }

    companion object {
        private const val EXPANDABLE_WIDGET_HELPER_KEY = "ExpandableWidgetHelper"

        private const val EXPANDABLE_EXTENDED_FLOATING_ACTION_BUTTON_KEY = "ExpandableExtendedFloatingActionButton"
        private const val IS_EXTENDED_KEY = "IsExtended"
        private const val WAS_EXTENDED_KEY = "WasExtended"
    }
}