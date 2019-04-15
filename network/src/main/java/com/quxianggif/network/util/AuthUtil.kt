package com.quxianggif.network.util

import android.text.TextUtils
import com.example.core.Const
import com.example.core.util.ShareUtil
import java.lang.StringBuilder

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/15 下午3:24
 * Describe:服务器身份验证相关的工具类
 */
object AuthUtil {

    //判断用户是否已登录
    val isLogin:Boolean
        get() {
            val u=ShareUtil.read(Const.Auth.USER_ID,0L)
            val t=ShareUtil.read(Const.Auth.TOKEN,"")
            val lt=ShareUtil.read(Const.Auth.LOGIN_TYPE,-1)
            return u>0&&!TextUtils.isEmpty(t)&&lt>=0
        }

    //获取当前登录用户id
    val userId:Long
        get() = ShareUtil.read(Const.Auth.USER_ID,0L)

    //获取当前登录用户的token。
    val token:String
        get() = ShareUtil.read(Const.Auth.TOKEN,"")

    //获取服务器校验码。使用和服务器端相同的算法生成服务器校验码，对接口的安全性进行保护，防止对服务器进行恶意攻击
    fun getServerVerifyCode(vararg params:String):String{
        if(params.isNotEmpty()){
            val builder=StringBuilder()
            var needSeparator=false
            for(param in params){
                if(needSeparator){
                    builder.append(",")
                }
                builder.append(param)
                needSeparator=true
            }
            return MD5.encrypt(builder.toString())
        }
        return ""
    }
}