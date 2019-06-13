package com.example.main.feeds.ui

import android.app.Activity
import android.content.Intent
import com.example.main.ui.BaseActivity

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/12 下午6:03
 * Describe:
 */
class DraftActivity : BaseActivity(){

    companion object {

        fun actionStart(activity: Activity) {
            val intent = Intent(activity, DraftActivity::class.java)
            activity.startActivity(intent)
        }

        const val TAG = "DraftActivity"
    }

}