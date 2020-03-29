package com.leeewy.fabwithspeeddial.fragments

import android.content.res.Configuration
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.leeewy.fabwithspeeddial.widgets.CollapsingAppBarLayout

abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {

    fun manageCollapsingAppBar(recyclerView: RecyclerView, collapsingAppBarLayout: CollapsingAppBarLayout) {
        recyclerView.apply {
            (layoutParams as CoordinatorLayout.LayoutParams).behavior = AppBarLayout.ScrollingViewBehavior()
            requestLayout()
        }

        collapsingAppBarLayout.enableScrolling(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT, true)
    }
}