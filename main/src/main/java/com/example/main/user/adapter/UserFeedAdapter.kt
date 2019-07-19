package com.example.main.user.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.core.extension.dp2px
import com.example.core.model.UserFeed
import com.example.main.R
import com.example.main.common.adapter.SimpleListFeedAdapter
import com.example.main.user.ui.UserHomePageActivity

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/14 上午10:33
 * Describe:用户个人主页的适配器。
 */
class UserFeedAdapter(override var activity: UserHomePageActivity, feedList: MutableList<UserFeed>,
                      maxImageWidth: Int, layoutManager: RecyclerView.LayoutManager) : SimpleListFeedAdapter<UserFeed, UserHomePageActivity>(activity, feedList, maxImageWidth, layoutManager) {


    override var isLoadFailed: Boolean = false
        get() = activity.isLoadFailed

    override var isNoMoreData: Boolean = false
        get() = activity.isNoMoreData

    override fun onLoad() {
        activity.onLoad()
    }

    override fun createFeedHolder(parent: ViewGroup): FeedViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.user_feed_item, parent, false)
        val holder = FeedViewHolder(view)
        initBaseFeedHolder(holder)
        return holder
    }

    override fun createRefeedHolder(parent: ViewGroup): RefeedViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.user_refeed_item, parent, false)
        val holder = RefeedViewHolder(view)
        initBaseFeedHolder(holder)
        return holder
    }

    override fun bindFeedHolder(holder: FeedViewHolder, position: Int) {
        setupFirstItemMarginTop(holder.cardView, position)
        super.bindFeedHolder(holder, position)
    }

    override fun bindRefeedHolder(holder: RefeedViewHolder, position: Int) {
        setupFirstItemMarginTop(holder.cardView, position)
        super.bindRefeedHolder(holder, position)
    }

    private fun setupFirstItemMarginTop(cardView: CardView, position: Int) {
        val params = if (position == 0) {
            val layoutParams = cardView.layoutParams as RecyclerView.LayoutParams
            layoutParams.topMargin = dp2px(35f)
            layoutParams
        } else {
            val layoutParams = cardView.layoutParams as RecyclerView.LayoutParams
            layoutParams.topMargin = dp2px(10f)
            layoutParams
        }

        cardView.layoutParams = params

    }

    companion object {
        private const val TAG = "UserFeedAdapter"
    }


}