package com.accordion.view.animation

import android.view.View
import android.view.ViewGroup
import com.accordion.view.animation.IAccordionAdapter.ViewHolder

interface IAccordionAdapter<T : ViewHolder, C : ViewHolder> {

    fun createTitleViewHolder(parent: ViewGroup): T

    fun createContentViewHolder(parent: ViewGroup): C

    fun bindTitle(viewHolder: T, position: Int, titleType: TitleType)

    fun bindContent(viewHolder: C, position: Int)

    fun getItemCount(): Int

    open class ViewHolder(val itemView: View)

}
