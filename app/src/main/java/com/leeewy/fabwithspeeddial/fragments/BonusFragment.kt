package com.leeewy.fabwithspeeddial.fragments

import android.os.Bundle
import android.view.View
import com.leeewy.fabwithspeeddial.R
import com.leeewy.fabwithspeeddial.adapters.MockItemsAdapter
import com.leeewy.fabwithspeeddial.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_default_fab.*

class BonusFragment : BaseFragment(R.layout.fragment_bonus) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.post {
            recycler_view.adapter = MockItemsAdapter()
        }
    }

    override fun buildSpeedDial() {

    }
}
