package com.leeewy.fabwithspeeddial.widgets

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Parcelable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.leeewy.fabwithspeeddial.R

class ExpandableFloatingActionButton : FloatingActionButton {

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
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.ExpandableFloatingActionButton, 0, 0)

        try {
            animationDuration = a.getInteger(R.styleable.ExpandableFloatingActionButton_animationDuration, 500)
            expandingDrawable = a.getResourceId(R.styleable.ExpandableFloatingActionButton_expandingDrawable, 0)
            collapsingDrawable = a.getResourceId(R.styleable.ExpandableFloatingActionButton_collapsingDrawable, 0)
            expandedIconColor = a.getColor(R.styleable.ExpandableFloatingActionButton_expandedIconColor, 0)
            collapsedIconColor = a.getColor(R.styleable.ExpandableFloatingActionButton_collapsedIconColor, 0)
            expandedBackgroundColor = a.getColor(R.styleable.ExpandableFloatingActionButton_expandedBackgroundColor, 0)
            collapsedBackgroundColor = a.getColor(R.styleable.ExpandableFloatingActionButton_collapsedBackgroundColor, 0)
        } finally {
            a.recycle()
        }
    }

    override fun setExpanded(expanded: Boolean): Boolean {
        return super.setExpanded(expanded).also {
            animateExpandingAndCollapsing()
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)

        animateExpandingAndCollapsing()
    }

    private fun animateExpandingAndCollapsing() {
        animateIconDrawable()

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
                setImageDrawable(it)
            } as AnimatedVectorDrawable).start()
        }
    }

    private fun getIconColorAnimator(): Animator? {
        return if (expandedIconColor != 0 && collapsedIconColor != 0) {
            createColorAnimator(expandedIconColor, collapsedIconColor).apply {
                duration = animationDuration.toLong()
                addUpdateListener { animator ->
                    imageTintList = ColorStateList.valueOf(animator.animatedValue as Int)
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

    private fun createColorAnimator(@ColorInt expandedColorId: Int, @ColorInt collapsedColorId: Int): ValueAnimator {
        return ValueAnimator.ofObject(
            ArgbEvaluator(),
            if (isExpanded) expandedColorId else collapsedColorId,
            if (isExpanded) collapsedColorId else expandedColorId
        )
    }
}