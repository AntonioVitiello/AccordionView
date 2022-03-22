package com.accordion.view.scroll.accordion

import android.view.View
import android.view.ViewGroup
import com.accordion.view.scroll.accordion.AccordionViewAdapter.ViewHolder
import java.util.*

abstract class AccordionViewAdapter<T : ViewHolder, C : ViewHolder> : Observable() {
    enum class TitleType {
        COLLAPSE,
        EXPAND
    }

    companion object {
        const val COLLAPS_SYMBOL = "\u2212" // ➖
        const val EXPAND_SYMBOL = "\u002B" // ➕
    }


    abstract fun createTitleViewHolder(parent: ViewGroup): ViewHolder

    abstract fun createContentViewHolder(parent: ViewGroup): ViewHolder

    abstract fun getItemCount(): Int

    abstract fun bindTitle(viewHolder: T, position: Int, titleType: TitleType)

    abstract fun bindContent(viewHolder: C, position: Int)

    fun notifyDataSetChanged() {
        setChanged()
        notifyObservers()
    }


    open class ViewHolder(val itemView: View)

}
