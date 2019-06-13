package com.example.main.feeds.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.core.model.HotFeed
import com.example.core.util.GlobalUtil
import com.example.main.R
import com.example.main.comments.ui.CommentsActivity
import com.example.main.feeds.ui.HotFeedsFragment

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/12 上午11:22
 * Describe:热门Feed模块的RecyclerView适配器，用于在界面上展示热门Feed数据。
 */
class HotFeedAdapter(private val fragment:HotFeedsFragment,private val feedList:List<HotFeed>,imageWidth:Int,
                     layoutManager: RecyclerView.LayoutManager):WaterFallFeedAdapter<HotFeed>(fragment.activity,feedList,imageWidth,layoutManager) {
    override var isLoadFailed: Boolean=false
        get() = fragment.isLoadFailed


    override var isNoMoreData: Boolean=false
        get() = fragment.isNoMoreData

    override fun onLoad() {
        fragment.onLoad()
    }

    override fun createFeedHolder(parent: ViewGroup): FeedViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.hot_feed_item, parent, false)
        val holder = HotFeedViewHolder(view)
        holder.commentsCount.setOnClickListener {
            val feedPosition=holder.adapterPosition
            val feed=feedList[feedPosition]
            CommentsActivity.actionStart(activity,feed.feedId)
        }
        baseCreateFeedHolder(holder)
        return holder
    }

    override fun bindFeedHolder(holder: FeedViewHolder, position: Int) {
        val viewHolder = holder as HotFeedViewHolder
        val feed = feedList[position]
        viewHolder.commentsCount.text = GlobalUtil.getConvertedNumber(feed.commentsCount)
        baseBindFeedHolder(viewHolder, position)
    }

    private class HotFeedViewHolder internal constructor(view: View): WaterFallFeedAdapter.FeedViewHolder(view){
        val commentsCount: TextView = view.findViewById(R.id.commentsCount)

        val commentsLayout: LinearLayout = view.findViewById(R.id.commentsLayout)
    }

    companion object {

        private const val TAG = "HotFeedAdapter"
    }
}