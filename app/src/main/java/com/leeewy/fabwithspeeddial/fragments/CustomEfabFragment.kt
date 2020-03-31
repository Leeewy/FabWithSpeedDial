package com.leeewy.fabwithspeeddial.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import com.leeewy.fabwithspeeddial.R
import com.leeewy.fabwithspeeddial.adapters.MockItemsAdapter
import com.leeewy.fabwithspeeddial.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_custom_efab.*
import kotlinx.android.synthetic.main.layout_custom_speed_dial.*

class CustomEfabFragment : BaseFragment(R.layout.fragment_custom_efab) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.post {
            recycler_view.adapter = MockItemsAdapter()
        }
    }

    override fun buildSpeedDial() {
        (fabs_container.layoutParams as ViewGroup.MarginLayoutParams).apply {
            setMargins(
                fabs_container.marginStart,
                fabs_container.marginTop,
                resources.getDimensionPixelSize(R.dimen.margin_quarter),
                resources.getDimensionPixelSize(R.dimen.default_main_fab_size) + (2 * resources.getDimensionPixelSize(R.dimen.margin))
            )
        }
        fabs_container.requestLayout()

        View.OnClickListener {
            animateSpeedDialLayout()
        }.let {
            fab_main.setOnClickListener(it)
            speed_dial_background.setOnClickListener(it)
        }

        fab_first.setOnClickListener {
            animateSpeedDialLayout()

            Toast.makeText(requireContext(), "First FAB clicked", Toast.LENGTH_SHORT).show()
        }

        fab_second.setOnClickListener {
            animateSpeedDialLayout()

            Toast.makeText(requireContext(), "Second FAB clicked", Toast.LENGTH_SHORT).show()
        }

        fab_third.setOnClickListener {
            animateSpeedDialLayout()

            Toast.makeText(requireContext(), "Third FAB clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun animateSpeedDialLayout() {
        fab_main.isExpanded = !fab_main.isExpanded

        collapsing_app_bar_layout.enableScrolling(enable = !fab_main.isExpanded, changeExpandedState = false)
    }
}
