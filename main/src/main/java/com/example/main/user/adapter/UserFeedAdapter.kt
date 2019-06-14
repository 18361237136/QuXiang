package com.example.main.user.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.core.model.UserFeed
import com.example.main.common.adapter.SimpleListFeedAdapter
import com.example.main.user.ui.UserHomePageActivity

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/14 上午10:33
 * Describe:用户个人主页的适配器。
 */
class UserFeedAdapter(override var activity: UserHomePageActivity, feedList: MutableList<UserFeed>,
                      maxImageWidth: Int, layoutManager: RecyclerView.LayoutManager) : SimpleListFeedAdapter<UserFeed, UserHomePageActivity>(activity, feedList, maxImageWidth, layoutManager) {
    override fun onLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createFeedHolder(parent: ViewGroup): FeedViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createRefeedHolder(parent: ViewGroup): RefeedViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override var isLoadFailed: Boolean = false
        get() = activity.isLoadFailed

    override var isNoMoreData: Boolean = false
        get() = activity.isNoMoreData
}