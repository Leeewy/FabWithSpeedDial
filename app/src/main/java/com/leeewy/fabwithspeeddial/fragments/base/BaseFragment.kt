package com.leeewy.fabwithspeeddial.fragments.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.leeewy.fabwithspeeddial.R
import com.leeewy.fabwithspeeddial.widgets.CollapsingAppBarLayout

abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // workaround for CollapsingAppBarLayout and list height (behavior need to be set after each recreation)
        view.findViewById<RecyclerView>(R.id.recycler_view)?.apply {
            (layoutParams as CoordinatorLayout.LayoutParams).behavior = AppBarLayout.ScrollingViewBehavior()
            requestLayout()
        }

        view.findViewById<CollapsingAppBarLayout>(R.id.collapsing_app_bar_layout)?.enableScrolling()

        buildSpeedDial()
    }

    abstract fun buildSpeedDial()

    fun setSpeedDialMargins(container: View, marginEnd: Int, marginBottom: Int) {
        (container.layoutParams as ViewGroup.MarginLayoutParams).setMargins(
            container.marginStart,
            container.marginTop,
            marginEnd,
            marginBottom
        )
        container.requestLayout()
    }
}