package com.leeewy.fabwithspeeddial.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.leeewy.fabwithspeeddial.R

class MockItemsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_VIEW) {
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_mock, parent, false))
        } else {
            FooterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_footer_empty, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.mockItem.setCardBackgroundColor(
                ContextCompat.getColor(holder.mockItem.context, R.color.color_item)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position < LIST_SIZE -> ITEM_VIEW
            else -> FOOTER_BUFFER_VIEW
        }
    }

    override fun getItemCount(): Int = LIST_SIZE + 1

    inner class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mockItem: MaterialCardView = view.findViewById(R.id.card_view)
    }

    companion object {
        private const val LIST_SIZE = 20

        private const val FOOTER_BUFFER_VIEW = 0
        private const val ITEM_VIEW = 1
    }
}