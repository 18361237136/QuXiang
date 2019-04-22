package com.example.quxiang.opensource

import android.os.Bundle
import com.example.main.init.ui.SplashActivity
import com.example.quxiang.R
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/2 下午7:05
 * Describe:开源版闪屏Activity界面
 */
class OpenSourceSplashActivity:SplashActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        logoView=logo
    }
}