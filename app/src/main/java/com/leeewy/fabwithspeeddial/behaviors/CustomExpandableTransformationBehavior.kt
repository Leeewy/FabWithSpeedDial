package com.leeewy.fabwithspeeddial.behaviors

import android.animation.*
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.animation.addListener
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transformation.ExpandableTransformationBehavior
import com.leeewy.fabwithspeeddial.R

class CustomExpandableTransformationBehavior(
    private val context: Context,
    attrs: AttributeSet? = null
) : ExpandableTransformationBehavior(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return (dependency is FloatingActionButton || dependency is ExtendedFloatingActionButton) && child is ViewGroup
    }

    override fun onCreateExpandedStateChangeAnimation(dependency: View, child: View, expanded: Boolean, isAnimating: Boolean): AnimatorSet {
        if (child !is ViewGroup) {
            return AnimatorSet()
        }

        return AnimatorSet().apply {
            playTogether(
                createFabsAnimation(child.findViewById(R.id.fabs_container), expanded, isAnimating),
                createBackgroundColorAnimator(child, expanded)
            )
            addListener(
                onStart = { if (expanded) child.isVisible = true },
                onEnd = { if (!expanded) child.isInvisible = true }
            )
        }
    }

    private fun createFabsAnimation(child: ViewGroup, expanded: Boolean, isAnimating: Boolean): AnimatorSet {
        if (expanded && !isAnimating) {
            child.children.forEach {
                it.alpha = 0f
                it.scaleX = 0.4f
                it.scaleY = 0.4f
            }
        }

        val delays = List(child.childCount) {
            it * DELAY
        }

        return AnimatorSet().apply {
            playTogether(
                child.children.zip(if (expanded) delays.reversed().asSequence() else delays.asSequence()) { view, delay ->
                    ObjectAnimator.ofPropertyValuesHolder(
                        view,
                        PropertyValuesHolder.ofFloat(View.SCALE_X, if (expanded) 1f else 0.4f),
                        PropertyValuesHolder.ofFloat(View.SCALE_Y, if (expanded) 1f else 0.4f),
                        PropertyValuesHolder.ofFloat(View.ALPHA, if (expanded) 1f else 0f)
                    ).apply {
                        duration = getDefaultAnimationDuration() / child.childCount
                        startDelay = delay
                    }
                }.toList()
            )
        }.apply {
            addListener(
                onStart = { if (expanded) child.isVisible = true },
                onEnd = { if (!expanded) child.isInvisible = true }
            )
        }
    }

    private fun createBackgroundColorAnimator(speedDialBackgroundView: View, expanded: Boolean): Animator {
        return ValueAnimator.ofObject(
            ArgbEvaluator(),
            if (expanded) Color.TRANSPARENT else ContextCompat.getColor(context, R.color.color_background_speed_dial),
            if (expanded) ContextCompat.getColor(context, R.color.color_background_speed_dial) else Color.TRANSPARENT
        ).apply {
            duration = getDefaultAnimationDuration()
            addUpdateListener { animator -> speedDialBackgroundView.setBackgroundColor(animator.animatedValue as Int) }
        }
    }

    private fun getDefaultAnimationDuration(): Long = context.resources.getInteger(R.integer.anim_duration).toLong()

    companion object {
        private const val DELAY = 50L
    }
}