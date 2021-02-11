package com.example.main.feeds.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.core.model.WorldFeed
import com.example.main.R
import com.example.main.common.adapter.WaterFallFeedAdapter
import com.example.main.feeds.ui.WorldFeedsFragment

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/30 下午5:16
 * Describe:世界模块的RecyclerView适配器，用于在界面上展示世界模块的数据以及处理世界模块的相关功能
 */
class WorldFeedAdapter(private val fragment: WorldFeedsFragment, feedList:List<WorldFeed>, imageWidth:Int,
                       layoutManager: RecyclerView.LayoutManager): WaterFallFeedAdapter<WorldFeed>(fragment.activity, feedList, imageWidth, layoutManager) {

    override var isLoadFailed: Boolean = false
        get() = fragment.isLoadFailed

    override var isNoMoreData: Boolean = false
        get() = fragment.isNoMoreData

    override fun onLoad() {
        onLoad()
    }

    override fun createFeedHolder(parent: ViewGroup): FeedViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.world_feed_item, parent, false)
        val holder = WorldFeedViewHolder(view)
        baseCreateFeedHolder(holder)
        return holder
    }

    override fun bindFeedHolder(holder: FeedViewHolder, position: Int) {
        val viewHolder = holder as WorldFeedViewHolder
        baseBindFeedHolder(viewHolder, position)
    }

    private class WorldFeedViewHolder internal constructor(view: View): WaterFallFeedAdapter.FeedViewHolder(view)

}