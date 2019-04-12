package com.example.main.init.ui

import android.os.Bundle
import android.view.View
import com.example.core.GifFun
import com.example.core.model.Version
import com.example.core.util.GlobalUtil
import com.example.main.feeds.ui.MainActivity
import com.example.main.login.ui.LoginActivity
import com.example.main.ui.BaseActivity

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/8 上午11:23
 * Describe:闪屏Activity
 */
abstract class SplashActivity :BaseActivity(){

    //记录进入SplashActivity的时间
    var enterTime:Long=0

    //判断是否正在跳转或者已经跳转到下一个界面
    var isForwarding=false

    var hasNewVersion=false

    lateinit var logoView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTime=System.currentTimeMillis()
        delayToForward()
    }

    override fun setupViews() {
        startInitRequest()
    }

    //设置闪屏界面的最大延迟跳转，让用户不至于在闪屏界面等太久
    private fun delayToForward(){
        Thread(Runnable {
            GlobalUtil.sleep(MAX_WAIT_TIME.toLong())
            forwardToNextActivity(false,null)
        }).start()
    }

    //跳转到下一个Activity.如果闪屏界面停留的时间还不足最短时间，要等待一会，保证闪屏不会一闪而过
    open fun forwardToNextActivity(hasNewVersion:Boolean,version: Version?){
        if(!isForwarding){
            isForwarding=true
            val currentTime=System.currentTimeMillis()
            val timeSpent=currentTime-enterTime
            if(timeSpent< MIN_WAIT_TIME){
                GlobalUtil.sleep(MIN_WAIT_TIME-timeSpent)
            }
            runOnUiThread{
                if(GifFun.isLogin()){
                    MainActivity.actionStart(this)
                    finish()
                }else{
                    if(isActive){
                        LoginActivity.actionStartWithTransition(this,logoView,hasNewVersion,version)
                    }else{
                        LoginActivity.actionStart(this,hasNewVersion,version)
                        finish()
                    }
                }
            }
        }
    }

    //开始向服务器发送初始化请求
    private fun startInitRequest(){

    }

    companion object {
        private const val TAG="SplashActivity"

        //闪屏的最短时间
        const val MIN_WAIT_TIME=2000

        //闪屏的最长时间
        const val MAX_WAIT_TIME=5000
    }
}