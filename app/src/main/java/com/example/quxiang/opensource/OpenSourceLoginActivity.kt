package com.example.quxiang.opensource

import android.os.Bundle
import com.example.main.login.ui.LoginActivity
import com.example.quxiang.R

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/22 下午5:55
 * Describe:开源版界面登录，支持手机号登录，如果登陆的账号没有注册就会跳转到注册界面如果已经注册过了就直接会跳转到主界面
 */
class OpenSourceLoginActivity :LoginActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
    override fun forwardToMainActivity() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}