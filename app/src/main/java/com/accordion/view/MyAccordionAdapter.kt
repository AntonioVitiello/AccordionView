package com.accordion.view

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.accordion.view.IAccordionAdapter.ViewHolder
import com.accordion.view.MyAccordionAdapter.ContentViewHolder
import com.accordion.view.MyAccordionAdapter.TitleViewHolder
import kotlinx.android.synthetic.main.accordion_content_view.view.*
import kotlinx.android.synthetic.main.accordion_title_view.view.*

/**
 * Created by Antonio Vitiello on 13/03/2022.
 */
class MyAccordionAdapter() : IAccordionAdapter<TitleViewHolder, ContentViewHolder> {
    private val mDataModels = mutableListOf<DataModel>()

    override fun createTitleViewHolder(parent: ViewGroup): TitleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.accordion_title_view, parent, false)
        return TitleViewHolder(itemView)
    }

    override fun createContentViewHolder(parent: ViewGroup): ContentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.accordion_content_view, parent, false)
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
                    TitleType.COLLAPSE -> titleIconView.html(context.getString(R.string.collaps_symbol))
                    TitleType.EXPAND -> titleIconView.html(context.getString(R.string.expand_symbol))
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