package com.accordion.view.animation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.accordion.view.R
import com.accordion.view.animation.IAccordionAdapter.ViewHolder
import com.accordion.view.animation.MyAccordionAdapter.ContentViewHolder
import com.accordion.view.animation.MyAccordionAdapter.TitleViewHolder
import com.accordion.view.html
import kotlinx.android.synthetic.main.accordion_content_view_a.view.*
import kotlinx.android.synthetic.main.accordion_title_view.view.*

/**
 * Created by Antonio Vitiello on 13/03/2022.
 */
class MyAccordionAdapter() : IAccordionAdapter<TitleViewHolder, ContentViewHolder> {
    private val mDataModels = mutableListOf<DataModel>()

    companion object {
        const val COLLAPS_SYMBOL = "\u2212" // ➖
        const val EXPAND_SYMBOL = "\u002B" // ➕
    }


    override fun createTitleViewHolder(parent: ViewGroup): TitleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.accordion_title_view, parent, false)
        return TitleViewHolder(itemView)
    }

    override fun createContentViewHolder(parent: ViewGroup): ContentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.accordion_content_view_a, parent, false)
        return ContentViewHolder(itemView)
    }

    override fun getItemCount() = mDataModels.size

    override fun bindTitle(viewHolder: TitleViewHolder, position: Int, titleType: TitleType) {
        val dataModel = mDataModels[position]
        viewHolder.bind(dataModel, titleType)
    }

    override fun bindContent(viewHolder: ContentViewHolder, position: Int) {
        val dataModel = mDataModels[position]
        viewHolder.bind(dataModel)
    }

    fun switchData(dataModels: List<DataModel>?) {
        mDataModels.clear()
        if (dataModels != null) {
            mDataModels.addAll(dataModels)
        }
    }


    inner class TitleViewHolder(itemView: View) : ViewHolder(itemView) {
        fun bind(dataModel: DataModel, titleType: TitleType) {
            itemView.apply {
                titleTextView.text = dataModel.title
                when (titleType) {
                    TitleType.COLLAPSE -> titleIconView.html(COLLAPS_SYMBOL)
                    TitleType.EXPAND -> titleIconView.html(EXPAND_SYMBOL)
                }
            }
        }
    }

    inner class ContentViewHolder(itemView: View) : ViewHolder(itemView) {
        fun bind(dataModel: DataModel) {
            itemView.apply {
                contentTextView.text = dataModel.desc
            }
        }
    }

}