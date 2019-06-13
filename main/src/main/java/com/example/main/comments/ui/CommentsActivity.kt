package com.example.main.comments.ui

import android.app.Activity
import android.content.Intent
import com.example.main.common.callback.LoadDataListener
import com.example.main.ui.BaseActivity

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/12 下午2:59
 * Describe:评论界面。用于显示一条Feed下的所有评论内容。
 */
class CommentsActivity : BaseActivity(), LoadDataListener {
    override fun onLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        private const val TAG = "CommentsActivity"

        const val FEED_ID = "feed_id"

        fun actionStart(activity: Activity, feedId: Long) {
            val intent = Intent(activity, CommentsActivity::class.java)
            intent.putExtra(FEED_ID, feedId)
            activity.startActivity(intent)
        }
    }
}