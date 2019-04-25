package com.example.main.feeds.ui

import android.app.Activity
import android.content.Intent
import com.example.main.ui.BaseActivity

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/11 下午6:27
 * Describe:
 */
class MainActivity :BaseActivity(){

    companion object {

        private const val TAG="MainActivity"

        private const val REQUEST_SEARCH=10000

        fun actionStart(activity: Activity){
            val intent= Intent(activity,MainActivity::class.java)
            activity.startActivity(intent)
        }
    }
}