package com.accordion.view.scroll.with_recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.accordion.view.R
import com.accordion.view.html
import com.accordion.view.scroll.with_recyclerview.TitleAccordionViewAdapter.ContentViewHolder
import com.accordion.view.scroll.with_recyclerview.TitleAccordionViewAdapter.TitleViewHolder
import com.accordion.view.scroll.accordion.AccordionViewAdapter
import kotlinx.android.synthetic.main.accordion_content_view_r.view.*
import kotlinx.android.synthetic.main.accordion_title_view.view.*

/**
 * Created by Antonio Vitiello on 13/03/2022.
 */
class TitleAccordionViewAdapter(context: Context, listener: (String) -> Unit) :
    AccordionViewAdapter<TitleViewHolder, ContentViewHolder>() {
    
    private val mDataModels = mutableListOf<DataModel>()
    private val mAdapter = ContentRecyclerViewAdapter(context, listener)


    override fun createTitleViewHolder(parent: ViewGroup): TitleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.accordion_title_view, parent, false)
        return TitleViewHolder(itemView)
    }

    override fun createContentViewHolder(parent: ViewGroup): ContentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.accordion_content_view_r, parent, false)
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

    fun switchData(data: List<DataModel>?) {
        mDataModels.clear()
        if (data != null) {
            mDataModels.addAll(data)
        }
        notifyDataSetChanged()
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
        init {
            itemView.contentRecycler.adapter = mAdapter
        }

        fun bind(dataModel: DataModel) {
            mAdapter.switchData(dataModel.desc)
        }
    }

}