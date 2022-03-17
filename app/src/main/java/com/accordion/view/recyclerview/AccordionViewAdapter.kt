package com.accordion.view.recyclerview

import android.view.View
import android.view.ViewGroup
import com.accordion.view.recyclerview.AccordionViewAdapter.ViewHolder
import java.util.*

abstract class AccordionViewAdapter<T : ViewHolder, C : ViewHolder> : Observable() {
    companion object {
        const val COLLAPS_SYMBOL = "\u2212" // ➖
        const val EXPAND_SYMBOL = "\u002B" // ➕
    }

    abstract fun createTitleViewHolder(parent: ViewGroup): T

    abstract fun createContentViewHolder(parent: ViewGroup): C

    abstract fun bindTitle(viewHolder: T, position: Int, titleType: TitleType)

    abstract fun bindContent(viewHolder: C, position: Int)

    abstract fun getItemCount(): Int

    open class ViewHolder(val itemView: View)

    fun notifyDataSetChanged() {
        setChanged()
        notifyObservers()
    }

}
