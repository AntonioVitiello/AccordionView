package com.accordion.view.scroll.accordion

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ScrollView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.accordion.view.R
import com.accordion.view.scroll.accordion.AccordionViewAdapter.TitleType
import com.accordion.view.scroll.accordion.AccordionViewAdapter.ViewHolder

/**
 * Created by Antonio Vitiello on 13/03/2022.
 */
class AccordionView : ConstraintLayout {
    private val mTitleViewHolders = mutableListOf<ViewHolder>()
    private lateinit var mContentViewHolder: ViewHolder
    private var mSelectedPosition = 0
    private lateinit var mAdapter: AccordionViewAdapter<ViewHolder, ViewHolder>
    private val mHandler = Handler(Looper.getMainLooper())
    private var mTitleToScroll: View? = null
    private var mCountScrollRetry = 5
    private var mScrollView: ScrollView? = null


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        if (isInEditMode) {
            background = ContextCompat.getDrawable(context, R.drawable.ic_accordion)
        }
    }

    @Suppress("UNCHECKED_CAST", "UNUSED_ANONYMOUS_PARAMETER")
    fun <A, B> setAdapter(adapter: AccordionViewAdapter<A, B>) where A : ViewHolder, B : ViewHolder {
        mAdapter = adapter as AccordionViewAdapter<ViewHolder, ViewHolder>
        mAdapter.addObserver { observable, args ->
            render()
        }
        mScrollView = findScrollViewParentOrNull(this@AccordionView.parent)
        if (mAdapter.getItemCount() > 0) {
            render()
        }
    }

    private fun render() {
        createTitleViews()
        createContent()
        addAllViews()
        applyConstraint()
    }

    /**
     *  add titles to accordion view and store in a list
     */
    private fun createTitleViews() {
        mTitleViewHolders.clear()
        for (index in 0 until mAdapter.getItemCount()) {
            val titleViewHolder = mAdapter.createTitleViewHolder(this)
            titleViewHolder.itemView.apply {
                id = View.generateViewId()
                tag = id.toString()
            }
            titleViewHolder.itemView.setOnClickListener {
                if (index == mSelectedPosition && mContentViewHolder.itemView.isVisible) {
                    closeCurrentPosition()
                } else {
                    mContentViewHolder.itemView.isInvisible = true
                    expandPosition(index)
                    mContentViewHolder.itemView.isVisible = true
                    forceScrollTo(titleViewHolder.itemView)
                }
            }
            mAdapter.bindTitle(titleViewHolder, index, getItemAction(index))
            mTitleViewHolders.add(titleViewHolder)
        }
    }

    private fun forceScrollTo(titleView: View) {
        mScrollView?.let { scrollView ->
            if (titleView != mTitleToScroll) {
                mTitleToScroll = titleView
                mCountScrollRetry = 5
            }
            mHandler.postDelayed({
                val offset = (this@AccordionView.y + titleView.y).toInt()
                scrollView.smoothScrollTo(0, offset)
//                Log.d(
//                    "DEBUG",
//                    "mCountScrollRetry=" + mCountScrollRetry +
//                            ", canScrollVertically=" + scrollView.canScrollVertically(1) +
//                            ", scrollY=" + scrollView.scrollY +
//                            ", titleY=" + titleView.y.toInt() +
//                            ", offset=" + offset +
//                            ", AccordionView.y=" + this@AccordionView.y +
//                            ", title0.y=" + mTitleViewHolders[0].itemView.y +
//                            ", title1.y=" + mTitleViewHolders[1].itemView.y +
//                            ", title2.y=" + mTitleViewHolders[2].itemView.y +
//                            ", title3.y=" + mTitleViewHolders[3].itemView.y
//                )
                if (--mCountScrollRetry > 0 && scrollView.canScrollVertically(1) && scrollView.scrollY != offset) {
                    forceScrollTo(titleView)
                } else {
                    mTitleToScroll = null
                }
            }, 100)
        }
    }

    private fun findScrollViewParentOrNull(viewParent: ViewParent): ScrollView? {
        return if (viewParent is ScrollView) {
            viewParent
        } else {
            val parent = viewParent.parent
            if (parent != null) {
                findScrollViewParentOrNull(parent)
            } else {
                null
            }
        }
    }

    /**
     * Update all titles and expand content at selected position
     */
    fun expandPosition(selectedPosition: Int) {
        if (selectedPosition < mAdapter.getItemCount() && selectedPosition >= 0) {
            mSelectedPosition = selectedPosition
            applyConstraint()
            onBindAllViewHolders()
        }
    }

    private fun closeCurrentPosition() {
        val lastPosition = mAdapter.getItemCount() - 1
        mContentViewHolder.itemView.isInvisible = true
        expandPosition(lastPosition)
        mAdapter.bindTitle(mTitleViewHolders[lastPosition], lastPosition, TitleType.EXPAND)
        mContentViewHolder.itemView.isGone = true
    }

    private fun onBindAllViewHolders() {
        mTitleViewHolders.forEachIndexed { innerIndex, innerViewHolder ->
            mAdapter.bindTitle(innerViewHolder, innerIndex, getItemAction(innerIndex))
        }
        mAdapter.bindContent(mContentViewHolder, mSelectedPosition)
    }

    private fun createContent() {
        clearContentView()
        mContentViewHolder = mAdapter.createContentViewHolder(this)
        with(mContentViewHolder.itemView) {
            id = View.generateViewId()
            tag = id.toString()
        }
        mAdapter.bindContent(mContentViewHolder, mSelectedPosition)
    }

    private fun clearContentView() {
        if (::mContentViewHolder.isInitialized) {
            val viewParent = mContentViewHolder.itemView.parent
            if (viewParent is ViewGroup) {
                viewParent.removeAllViews()
            }
        }
    }

    private fun addAllViews() {
        mTitleViewHolders.forEach {
            addView(it.itemView)
        }
        addView(mContentViewHolder.itemView)
    }

    private fun getItemAction(index: Int): TitleType {
        return if (index != mSelectedPosition) {
            TitleType.EXPAND
        } else {
            TitleType.COLLAPSE
        }
    }

    private fun applyConstraint() {
        //apply constraint to title on top of selectedPosition
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        for (index in 0..mSelectedPosition) {
            val titleView = mTitleViewHolders[index].itemView
            constraintSet.clear(titleView.id, ConstraintSet.TOP)
            constraintSet.clear(titleView.id, ConstraintSet.BOTTOM)

            if (index == 0) {
                constraintSet.connect(titleView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            } else {
                val previousTitle = mTitleViewHolders[index - 1].itemView
                constraintSet.connect(titleView.id, ConstraintSet.TOP, previousTitle.id, ConstraintSet.BOTTOM)
            }
            constraintSet.connect(titleView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            constraintSet.connect(titleView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        }

        //apply constraint to title bottom of selectedPosition
        for (reversedIndex in mTitleViewHolders.size - 1 downTo mSelectedPosition + 1) {
            val titleView = mTitleViewHolders[reversedIndex].itemView
            constraintSet.clear(titleView.id, ConstraintSet.TOP)
            constraintSet.clear(titleView.id, ConstraintSet.BOTTOM)
            if (reversedIndex == mTitleViewHolders.size - 1) {
                constraintSet.connect(titleView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            } else {
                val previousTitle = mTitleViewHolders[reversedIndex + 1].itemView
                constraintSet.connect(titleView.id, ConstraintSet.BOTTOM, previousTitle.id, ConstraintSet.TOP)
            }
            constraintSet.connect(titleView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            constraintSet.connect(titleView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        }
        constraintSet.applyTo(this)

        //apply constraint to content
        val set2 = ConstraintSet()
        set2.clone(this)
        mContentViewHolder.itemView.let { contentView ->
            val positionAboveContent = mSelectedPosition
            val positionBelowContent = mSelectedPosition + 1

            val titleIdAboveContent = mTitleViewHolders[positionAboveContent].itemView.id
            val titleIdBelowContent = if (mSelectedPosition == mTitleViewHolders.size - 1) {
                ConstraintSet.PARENT_ID
            } else {
                mTitleViewHolders[positionBelowContent].itemView.id
            }
            constraintSet.clear(contentView.id, ConstraintSet.TOP)
            constraintSet.clear(contentView.id, ConstraintSet.BOTTOM)
            set2.connect(contentView.id, ConstraintSet.TOP, titleIdAboveContent, ConstraintSet.BOTTOM)
            set2.connect(
                contentView.id, ConstraintSet.BOTTOM, titleIdBelowContent, if (mSelectedPosition == mTitleViewHolders.size - 1) {
                    ConstraintSet.BOTTOM // align to parent's bottom if it is the last one
                } else {
                    ConstraintSet.TOP
                }
            )
            constraintSet.connect(contentView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            constraintSet.connect(contentView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        }
        set2.applyTo(this)
    }

}