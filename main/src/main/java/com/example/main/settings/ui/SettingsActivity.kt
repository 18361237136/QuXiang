package com.example.main.settings.ui

import android.app.Activity
import android.content.Intent
import com.example.main.ui.BaseActivity

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/12 下午6:06
 * Describe:App设置界面的主Activity。
 */
class SettingsActivity :BaseActivity(){

    companion object {

        private const val TAG = "SettingsActivity"

        const val MAIN_SETTINGS = 0

        const val GIF_SETTINGS = 1

        private const val INTENT_SETTINGS_TYPE = "intent_settings_type"

        fun actionStart(activity: Activity) {
            val intent = Intent(activity, SettingsActivity::class.java)
            activity.startActivity(intent)
        }

        fun actionStartGIFSettings(activity: Activity) {
            val intent = Intent(activity, SettingsActivity::class.java)
            intent.putExtra(INTENT_SETTINGS_TYPE, GIF_SETTINGS)
            activity.startActivity(intent)
        }
    }
}