package com.leeewy.fabwithspeeddial.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.leeewy.fabwithspeeddial.R
import com.leeewy.fabwithspeeddial.adapters.MockItemsAdapter
import com.leeewy.fabwithspeeddial.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_default_efab.*
import kotlinx.android.synthetic.main.layout_default_speed_dial.*

class DefaultEfabFragment : BaseFragment(R.layout.fragment_default_efab) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.post {
            recycler_view.adapter = MockItemsAdapter()
        }
    }

    override fun buildSpeedDial() {
        fab_main.setOnClickListener {
            fab_main.isExpanded = !fab_main.isExpanded
        }

        fab_first.setOnClickListener {
            fab_main.isExpanded = !fab_main.isExpanded

            Toast.makeText(requireContext(), "First FAB clicked", Toast.LENGTH_SHORT).show()
        }

        fab_second.setOnClickListener {
            fab_main.isExpanded = !fab_main.isExpanded

            Toast.makeText(requireContext(), "Second FAB clicked", Toast.LENGTH_SHORT).show()
        }

        fab_third.setOnClickListener {
            fab_main.isExpanded = !fab_main.isExpanded

            Toast.makeText(requireContext(), "Third FAB clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
