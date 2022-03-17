package com.accordion.view.recyclerview

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.accordion.view.R
import com.accordion.view.recyclerview.MyAccordionViewAdapter.ContentViewHolder
import com.accordion.view.recyclerview.MyAccordionViewAdapter.TitleViewHolder
import kotlinx.android.synthetic.main.accordion_content_view_r.view.*
import kotlinx.android.synthetic.main.accordion_title_view.view.*

/**
 * Created by Antonio Vitiello on 13/03/2022.
 */
class MyAccordionViewAdapter(context: Context, listener: (String) -> Unit) : AccordionViewAdapter<TitleViewHolder, ContentViewHolder>() {
    private val mDataModels = mutableListOf<DataModel>()
    private val mAdapter = AccordionViewContentAdapter(context, listener)

    companion object {
        const val COLLAPS_SYMBOL = "\u2212" // ➖
        const val EXPAND_SYMBOL = "\u002B" // ➕
    }


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

    @Suppress("DEPRECATION")
    fun TextView.html(html: CharSequence) {
        text = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Html.fromHtml(html.toString())
        } else {
            Html.fromHtml(html.toString(), Html.FROM_HTML_MODE_COMPACT)
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
        init {
            itemView.contentRecycler.adapter = mAdapter
        }

        fun bind(dataModel: DataModel) {
            mAdapter.switchData(dataModel.desc)
        }
    }

}