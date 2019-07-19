package com.example.main.user.ui

import android.app.Activity
import android.content.Intent
import com.example.main.ui.BaseActivity

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/7/19 下午3:05
 * Describe:用户个人主页关注和粉丝列表的Activity
 */
class FollowshipActivity :BaseActivity(){

    companion object{

        const val NICKNAME = "NICKNAME"

        const val USER_ID = "USER_ID"

        const val IS_GET_FOLLOWINGS = "IS_GET_FOLLOWINGS"

        fun actionFollowings(activity: Activity, userId: Long, nickname: String){
            val intent= Intent(activity,FollowshipActivity::class.java)
            intent.putExtra(USER_ID, userId)
            intent.putExtra(NICKNAME, nickname)
            intent.putExtra(IS_GET_FOLLOWINGS, true)
            activity.startActivity(intent)
        }

        fun actionFollowers(activity: Activity, userId: Long, nickname: String) {
            val intent = Intent(activity, FollowshipActivity::class.java)
            intent.putExtra(USER_ID, userId)
            intent.putExtra(NICKNAME, nickname)
            intent.putExtra(IS_GET_FOLLOWINGS, false)
            activity.startActivity(intent)
        }
    }
}