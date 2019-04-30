package com.example.main.feeds.adapter

import android.support.v7.widget.RecyclerView
import com.example.core.model.WorldFeed
import com.example.main.feeds.ui.WorldFeedsFragment

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/30 下午5:16
 * Describe:世界模块的RecyclerView适配器，用于在界面上展示世界模块的数据以及处理世界模块的相关功能
 */
class WorldFeedAdapter(private val fragment:WorldFeedsFragment, feedList:List<WorldFeed>, imageWidth:Int,
                       layoutManager: RecyclerView.LayoutManager, override var isNoMoreData: Boolean, override var isLoadFailed: Boolean):WaterFallFeedAdapter<WorldFeed>(fragment.activity, feedList, imageWidth, layoutManager) {
}