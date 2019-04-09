package com.example.main

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.example.core.GifFun
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import org.litepal.LitePal

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/8 下午4:57
 * Describe:GifFun自定义Application，在这里进行全局的初始化操作。
 */
open class GifFunApplication :Application(){

    override fun onCreate() {
        super.onCreate()
        GifFun.initialize(this)
        UMConfigure.init(this,UMConfigure.DEVICE_TYPE_PHONE,null)
        MobclickAgent.setCatchUncaughtExceptions(false)//关闭友盟的崩溃采集功能，使用腾讯Bugly
        LitePal.initialize(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}