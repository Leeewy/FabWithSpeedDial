package com.leeewy.fabwithspeeddial.fragments

import android.os.Bundle
import android.view.View
import com.leeewy.fabwithspeeddial.R
import com.leeewy.fabwithspeeddial.adapters.MockItemsAdapter
import kotlinx.android.synthetic.main.fragment_default.*

class ExtendedFragment : BaseFragment(R.layout.fragment_extended) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageCollapsingAppBar(recycler_view, collapsing_app_bar_layout)

        recycler_view.post {
            recycler_view.adapter = MockItemsAdapter()
        }
    }
}
