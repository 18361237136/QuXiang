package com.example.main.login.ui

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import android.widget.Toast
import com.example.core.GifFun
import com.example.core.extension.logError
import com.example.core.model.Version
import com.example.core.util.AndroidVersion
import com.example.main.R

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/11 下午6:48
 * Describe:登录界面
 */
abstract class LoginActivity :AuthActivity() {

    companion object {
        private const val TAG="LoginActivity"

        @JvmStatic val START_WITH_TRANSITION="start_with_transition"

        @JvmStatic val INTENT_HAS_NEW_VERSION="intent_has_new_version"

        @JvmStatic val INTENT_VERSION = "intent_version"

        private val ACTION_LOGIN="${GifFun.getPackageName()}.ACTION_LOGIN"

        private val ACTION_LOGIN_WITH_TRANSITION = "${GifFun.getPackageName()}.ACTION_LOGIN_WITH_TRANSITION"

        //启动LoginActivity
        fun actionStart(activity: Activity,hasNewVersion:Boolean,version:Version?){
            val intent=Intent(ACTION_LOGIN).apply {
                putExtra(INTENT_HAS_NEW_VERSION,hasNewVersion)
                putExtra(INTENT_VERSION,version)
            }
            activity.startActivity(intent)
        }

        //启动LoginActivity并附带Transition动画
        fun actionStartWithTransition(activity: Activity, logo: View, hasNewVersion:Boolean, version:Version?){
            val intent = Intent(ACTION_LOGIN_WITH_TRANSITION).apply {
                putExtra(INTENT_HAS_NEW_VERSION, hasNewVersion)
                putExtra(INTENT_VERSION, version)
            }
            if (AndroidVersion.hasLollipop()) {
                intent.putExtra(START_WITH_TRANSITION, true)
                val options = ActivityOptions.makeSceneTransitionAnimation(activity, logo,
                        activity.getString(R.string.transition_logo_splash))
                activity.startActivity(intent, options.toBundle())
            } else {
                activity.startActivity(intent)
                activity.finish()
            }
        }
    }

    //是否进行动画
    protected var isTransitioning=false


}