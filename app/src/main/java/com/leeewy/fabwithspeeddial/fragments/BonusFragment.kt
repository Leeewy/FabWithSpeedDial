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
import kotlinx.android.synthetic.main.fragment_bonus.*
import kotlinx.android.synthetic.main.layout_bonus_speed_dial.*
import kotlinx.android.synthetic.main.layout_bonus_speed_dial.fab_first
import kotlinx.android.synthetic.main.layout_bonus_speed_dial.fab_second
import kotlinx.android.synthetic.main.layout_bonus_speed_dial.fab_third
import kotlinx.android.synthetic.main.layout_bonus_speed_dial.fabs_container

class BonusFragment : BaseFragment(R.layout.fragment_bonus) {

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
            fabs_container.setOnClickListener(it)
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

        fab_fourth.setOnClickListener {
            animateSpeedDialLayout()

            Toast.makeText(requireContext(), "Fourth FAB clicked", Toast.LENGTH_SHORT).show()
        }

        fab_fifth.setOnClickListener {
            animateSpeedDialLayout()

            Toast.makeText(requireContext(), "Fifth FAB clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun animateSpeedDialLayout() {
        fab_main.isExpanded = !fab_main.isExpanded

        collapsing_app_bar_layout.enableScrolling(enable = !fab_main.isExpanded, changeExpandedState = false)
    }
}
