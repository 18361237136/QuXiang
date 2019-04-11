package com.example.main.login.ui

import com.example.main.ui.BaseActivity

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/11 下午6:49
 * Describe:登录和注册的基类，用于封装登录和注册时通用的逻辑
 */
abstract class AuthActivity :BaseActivity(){
    companion object {

        private const val TAG="AuthActivity"

        /**
         * QQ第三方登录的类型。
         */
        const val TYPE_QQ_LOGIN = 1

        /**
         * 微信第三方登录的类型。
         */
        const val TYPE_WECHAT_LOGIN = 2

        /**
         * 微博第三方登录的类型。
         */
        const val TYPE_WEIBO_LOGIN = 3

        /**
         * 手机号登录的类型。
         */
        const val TYPE_PHONE_LOGIN = 4

        /**
         * 游客登录的类型，此登录只在测试环境下有效，线上环境没有此项功能。
         */
        const val TYPE_GUEST_LOGIN = -1
    }

    protected abstract fun forwardToMainActivity()
}