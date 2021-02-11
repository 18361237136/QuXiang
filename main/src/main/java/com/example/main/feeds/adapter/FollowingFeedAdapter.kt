package com.example.main.feeds.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.core.model.FollowingFeed
import com.example.main.R
import com.example.main.common.adapter.SimpleListFeedAdapter
import com.example.main.feeds.ui.FollowingFeedsFragment
import com.example.main.user.ui.UserHomePageActivity

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/13 下午5:05
 * Describe:关注模块的RecyclerView适配器，用于在界面上展示关注的数据。
 */
class FollowingFeedAdapter(private val fragment:FollowingFeedsFragment,feedList:MutableList<FollowingFeed>,
                           maxImageWidth:Int,layoutManager:RecyclerView.LayoutManager): SimpleListFeedAdapter<FollowingFeed, Activity>(fragment.activity,feedList,maxImageWidth,layoutManager) {

    override var isLoadFailed: Boolean = false
        get() = fragment.isLoadFailed

    override var isNoMoreData: Boolean = false
        get() = fragment.isNoMoreData

    override fun createFeedHolder(parent: ViewGroup): FeedViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.user_feed_item, parent, false)
        val holder = SimpleListFeedAdapter.FeedViewHolder(view)
        initBaseFeedHolder(holder)
        setupUserClick(holder)
        return holder
    }

    override fun createRefeedHolder(parent: ViewGroup): RefeedViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.user_refeed_item, parent, false)
        val holder = SimpleListFeedAdapter.RefeedViewHolder(view)
        initBaseFeedHolder(holder)
        setupUserClick(holder)
        return holder
    }

    private fun setupUserClick(holder: SimpleListFeedAdapter.SimpleListFeedViewHolder) {
        val onUserClick = View.OnClickListener {
            val position = holder.adapterPosition
            val feed = feedList[position]
            UserHomePageActivity.actionStart(activity, holder.avatar, feed.userId, feed.nickname, feed.avatar, feed.bgImage)
        }
        holder.avatar.setOnClickListener(onUserClick)
        holder.nickname.setOnClickListener(onUserClick)
        holder.postDate.setOnClickListener(onUserClick)
    }

    override fun onLoad() {
        fragment.onLoad()
    }
}