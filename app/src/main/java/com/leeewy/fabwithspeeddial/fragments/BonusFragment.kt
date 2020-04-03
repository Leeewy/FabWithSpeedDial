package com.leeewy.fabwithspeeddial.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        setSpeedDialMargins(
            fabs_container,
            resources.getDimensionPixelSize(R.dimen.margin_quarter),
            resources.getDimensionPixelSize(R.dimen.default_main_fab_size) + (2 * resources.getDimensionPixelSize(R.dimen.margin))
        )

        setSpeedDialBottomShadow()

        View.OnClickListener {
            animateSpeedDialLayout()
        }.let {
            fab_main.setOnClickListener(it)
            fabs_container.setOnClickListener(it)
            bottom_shadow.setOnClickListener(it)
        }

        View.OnClickListener {
            animateSpeedDialLayout()

            Toast.makeText(requireContext(), "First FAB clicked", Toast.LENGTH_SHORT).show()
        }.let {
            fab_first.setOnClickListener(it)
            fab_first_text.setOnClickListener(it)
        }

        View.OnClickListener {
            animateSpeedDialLayout()

            Toast.makeText(requireContext(), "Second FAB clicked", Toast.LENGTH_SHORT).show()
        }.let {
            fab_second.setOnClickListener(it)
            fab_second_text.setOnClickListener(it)
        }

        View.OnClickListener {
            animateSpeedDialLayout()

            Toast.makeText(requireContext(), "Third FAB clicked", Toast.LENGTH_SHORT).show()
        }.let {
            fab_third.setOnClickListener(it)
            fab_third_text.setOnClickListener(it)
        }

        View.OnClickListener {
            animateSpeedDialLayout()

            Toast.makeText(requireContext(), "Fourth FAB clicked", Toast.LENGTH_SHORT).show()
        }.let {
            fab_fourth.setOnClickListener(it)
            fab_fourth_text.setOnClickListener(it)
        }

        View.OnClickListener {
            animateSpeedDialLayout()

            Toast.makeText(requireContext(), "Fifth FAB clicked", Toast.LENGTH_SHORT).show()
        }.let {
            fab_fifth.setOnClickListener(it)
            fab_fifth_text.setOnClickListener(it)
        }
    }

    private fun animateSpeedDialLayout() {
        fab_main.isExpanded = !fab_main.isExpanded

        collapsing_app_bar_layout.enableScrolling(enable = !fab_main.isExpanded, changeExpandedState = false)
    }

    private fun setSpeedDialBottomShadow() {
        (bottom_shadow.layoutParams as ViewGroup.LayoutParams).height = resources.getDimensionPixelSize(R.dimen.default_main_fab_size) + (2 * resources.getDimensionPixelSize(R.dimen.margin))
        bottom_shadow.requestLayout()
    }
}
