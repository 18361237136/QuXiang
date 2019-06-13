package com.example.main.feeds.ui

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.util.Pair
import android.view.View
import com.example.core.model.BaseFeed
import com.example.core.util.AndroidVersion
import com.example.core.util.GlobalUtil
import com.example.main.R
import com.example.main.ui.BaseActivity

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/12 上午11:06
 * Describe: Feed详情页面，在这里显示GIF图、评论、点赞等内容。
 */
class FeedDetailActivity : BaseActivity(){

    companion object{
        private const val TAG = "FeedDetailActivity"

        const val FEED = "feed"

        private const val GIF_LOADING = 0

        private const val GIF_LOAD_SUCCESS = 1

        private const val GIF_FETCH_URL_FAILED = 2

        private const val GIF_LOAD_FAILED = 3

        fun actionSatrt(activity: Activity, image: View,feed:BaseFeed){
            val intent=Intent(activity,FeedDetailActivity::class.java)
            intent.putExtra(FEED,feed)

            if(AndroidVersion.hasLollipop()){
                val options=ActivityOptions.makeSceneTransitionAnimation(activity,
                        Pair.create(image, GlobalUtil.getString(R.string.transition_feed_detail)),
                        Pair.create(image, GlobalUtil.getString(R.string.transition_feed_detail_bg)),
                        Pair.create(image, GlobalUtil.getString(R.string.transition_feed_detail_image_bg)))
                activity.startActivity(intent,options.toBundle())
            }else{
                activity.startActivity(intent)
            }
        }
    }
}