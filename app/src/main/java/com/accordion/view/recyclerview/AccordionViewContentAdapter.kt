package com.accordion.view.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.accordion.view.R
import kotlinx.android.synthetic.main.item_accordion_content.view.*

/**
 * Created by Antonio Vitiello on 15/03/2022.
 */
class AccordionViewContentAdapter(private val context: Context, val listener: (String) -> Unit) :
    RecyclerView.Adapter<AccordionViewContentAdapter.ViewHolder>() {

    private val mDataItems = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_accordion_content, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = mDataItems[position]
        holder.bind(dataItem)
    }

    override fun getItemCount(): Int = mDataItems.size

    @SuppressLint("NotifyDataSetChanged")
    fun switchData(data: List<String>?) {
        mDataItems.clear()
        if (data != null) {
            mDataItems.addAll(data)
        }
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: String) {
            itemView.contentTextView.text = data
            itemView.setOnClickListener { listener.invoke(data) }
        }
    }

}