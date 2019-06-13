package com.example.main.user.ui

import android.app.Activity
import android.content.Intent
import com.example.main.ui.BaseActivity

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/12 下午6:04
 * Describe:展示系统推荐关注的用户列表界面。
 */
class RecommendFollowingActivity : BaseActivity(){
    companion object {

        fun actionStart(activity: Activity) {
            val intent = Intent(activity, RecommendFollowingActivity::class.java)
            activity.startActivity(intent)
        }
    }

}