package com.example.main.login.ui

import com.example.core.Const
import com.example.core.GifFun
import com.example.core.extension.logWarn
import com.example.core.extension.showToast
import com.example.core.util.GlobalUtil
import com.example.core.util.ShareUtil
import com.example.main.R
import com.example.main.ui.BaseActivity
import com.example.main.util.ResponseHandler
import com.example.main.util.UserUtil
import com.quxianggif.network.model.Callback
import com.quxianggif.network.model.GetBaseInfo
import com.quxianggif.network.model.Response
import com.quxianggif.network.request.GetBaseInfoRequest
import java.lang.Exception

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

    //根据参数中传入的登录类型获取登录类型的名称
    protected fun getLoginTypeName(loginType:Int)=when(loginType){
        TYPE_QQ_LOGIN->"QQ"
        TYPE_WECHAT_LOGIN->"微信"
        TYPE_WEIBO_LOGIN->"微博"
        TYPE_GUEST_LOGIN->"游客"
        else->""
    }

    //存储用户身份的信息
    protected fun saveAuthData(userId:Long,token:String,loginType: Int){
        ShareUtil.save(Const.Auth.USER_ID,userId)
        ShareUtil.save(Const.Auth.TOKEN, token)
        ShareUtil.save(Const.Auth.LOGIN_TYPE, loginType)
        GifFun.refreshLoginState()
    }

    //获取当前登录用户的基本信息，包括昵称头像等
    protected fun getUserBaseInfo(){
        GetBaseInfo.getResponse(object :Callback{
            override fun onResponse(response: Response) {
                if(activity==null) return
                if(!ResponseHandler.handleResponse(response)){
                    val baseinfo = response as GetBaseInfo
                    val status = baseinfo.status
                    when(status){
                        0->{
                            UserUtil.saveNickname(baseinfo.nickname)
                            UserUtil.saveAvatar(baseinfo.avatar)
                            UserUtil.saveDescription(baseinfo.description)
                            UserUtil.saveBgImage(baseinfo.bgImage)
                            forwardToMainActivity()
                        }
                        10202->{
                            showToast(GlobalUtil.getString(R.string.get_baseinfo_failed_user_not_exist))
                            GifFun.logout()
                            finish()
                        }
                        else->{
                            logWarn(TAG, "Get user baseinfo failed. " + GlobalUtil.getResponseClue(status, baseinfo.msg))
                            showToast(GlobalUtil.getString(R.string.get_baseinfo_failed))
                            GifFun.logout()
                            finish()
                        }
                    }
                }else{
                    activity?.let {
                        if(it.javaClass.name=="club.giffun.app.LoginDialogActivity"){
                            finish()
                        }
                    }
                }
            }

            override fun onFailure(e: Exception) {
                logWarn(TAG, e.message, e)
                showToast(GlobalUtil.getString(R.string.get_baseinfo_failed))
                GifFun.logout()
                finish()
            }

        })
    }
}