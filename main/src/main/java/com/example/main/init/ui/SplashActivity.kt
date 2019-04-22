package com.example.main.init.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.core.Const
import com.example.core.GifFun
import com.example.core.extension.logError
import com.example.core.extension.logVerbose
import com.example.core.extension.logWarn
import com.example.core.model.Version
import com.example.core.util.GlobalUtil
import com.example.core.util.ShareUtil
import com.example.main.event.FinishActivityEvent
import com.example.main.event.MessageEvent
import com.example.main.feeds.ui.MainActivity
import com.example.main.login.ui.LoginActivity
import com.example.main.ui.BaseActivity
import com.example.main.util.ResponseHandler
import com.quxianggif.network.model.Init
import com.quxianggif.network.model.OriginThreadCallback
import com.quxianggif.network.model.Response
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.Exception

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/8 上午11:23
 * Describe:闪屏Activity
 */
abstract class SplashActivity :BaseActivity(){
    /**
     * 记录进入SplashActivity的时间。
     */
    var enterTime: Long = 0

    /**
     * 判断是否正在跳转或已经跳转到下一个界面。
     */
    var isForwarding = false

    var hasNewVersion = false

    lateinit var logoView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTime = System.currentTimeMillis()
        delayToForward()
    }

    override fun setupViews() {
        startInitRequest()
    }

    override fun onBackPressed() {
        // 屏蔽手机的返回键
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onMessageEvent(messageEvent: MessageEvent) {
        if (messageEvent is FinishActivityEvent) {
            if (javaClass == messageEvent.activityClass) {
                if (!isFinishing) {
                    finish()
                }
            }
        }
    }

    /**
     * 开始向服务器发送初始化请求。
     */
    private fun startInitRequest() {
        Init.getResponse(object : OriginThreadCallback {
            override fun onResponse(response: Response) {
                if (activity == null) {
                    return
                }
                var version: Version? = null
                val init = response as Init
                GifFun.BASE_URL = init.base
                if (!ResponseHandler.handleResponse(init)) {
                    val status = init.status
                    if (status == 0) {
                        val token = init.token
                        val avatar = init.avatar
                        val bgImage = init.bgImage
                        hasNewVersion = init.hasNewVersion
                        if (hasNewVersion) {
                            version = init.version
                        }
                        if (!TextUtils.isEmpty(token)) {
                            ShareUtil.save(Const.Auth.TOKEN, token)
                            if (!TextUtils.isEmpty(avatar)) {
                                ShareUtil.save(Const.User.AVATAR, avatar)
                            }
                            if (!TextUtils.isEmpty(bgImage)) {
                                ShareUtil.save(Const.User.BG_IMAGE, bgImage)
                            }
                            GifFun.refreshLoginState()
                        }
                    } else {
                        logWarn(TAG, GlobalUtil.getResponseClue(status, init.msg))
                    }
                }
                forwardToNextActivity(hasNewVersion, version)
            }

            override fun onFailure(e: Exception) {
                logWarn(TAG, e.message, e)
                forwardToNextActivity(false, null)
            }
        })
    }

    /**
     * 设置闪屏界面的最大延迟跳转，让用户不至于在闪屏界面等待太久。
     */
    private fun delayToForward() {
        Thread(Runnable {
            GlobalUtil.sleep(MAX_WAIT_TIME.toLong())
            forwardToNextActivity(false, null)
        }).start()
    }

    /**
     * 跳转到下一个Activity。如果在闪屏界面停留的时间还不足规定最短停留时间，则会在这里等待一会，保证闪屏界面不至于一闪而过。
     */
    @Synchronized
    open fun forwardToNextActivity(hasNewVersion: Boolean, version: Version?) {
        if (!isForwarding) { // 如果正在跳转或已经跳转到下一个界面，则不再重复执行跳转
            isForwarding = true
            val currentTime = System.currentTimeMillis()
            val timeSpent = currentTime - enterTime
            if (timeSpent < MIN_WAIT_TIME) {
                GlobalUtil.sleep(MIN_WAIT_TIME - timeSpent)
            }
            runOnUiThread {
                if (GifFun.isLogin()) {
                    MainActivity.actionStart(this)
                    finish()
                } else {
                    if (isActive) {
                        LoginActivity.actionStartWithTransition(this, logoView, hasNewVersion, version)
                    } else {
                        LoginActivity.actionStart(this, hasNewVersion, version)
                        finish()
                    }
                }
            }
        }
    }

    companion object {

        private const val TAG = "SplashActivity"

        /**
         * 应用程序在闪屏界面最短的停留时间。
         */
        const val MIN_WAIT_TIME = 2000

        /**
         * 应用程序在闪屏界面最长的停留时间。
         */
        const val MAX_WAIT_TIME = 5000
    }
}