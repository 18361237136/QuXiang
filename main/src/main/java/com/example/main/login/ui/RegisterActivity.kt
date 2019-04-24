package com.example.main.login.ui

import android.support.design.widget.TextInputLayout
import android.widget.EditText
import android.widget.TextView

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/24 下午4:22
 * Describe:当登录账号不存在时，跳转到注册界面来进行账号注册
 */
abstract class RegisterActivity :AuthActivity(),TextView.OnEditorActionListener{

    //登录的类型(QQ,微信等)
    protected var loginType=0

    /**
     * 是否正在注册中。
     */
    protected var isRegistering = false

    lateinit var nicknameEditText: EditText

    lateinit var nicknameLayout: TextInputLayout



}