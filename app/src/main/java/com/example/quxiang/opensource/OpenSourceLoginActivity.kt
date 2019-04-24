package com.example.quxiang.opensource

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.support.transition.Fade
import android.support.transition.TransitionManager
import android.transition.Transition
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.core.extension.logWarn
import com.example.core.extension.showToast
import com.example.core.util.AndroidVersion
import com.example.core.util.GlobalUtil
import com.example.main.common.callback.SimpleTransitionListener
import com.example.main.event.FinishActivityEvent
import com.example.main.login.ui.LoginActivity
import com.example.main.util.ResponseHandler
import com.example.quxiang.R
import com.quxianggif.network.model.Callback
import com.quxianggif.network.model.FetchVCode
import com.quxianggif.network.model.PhoneLogin
import com.quxianggif.network.model.Response
import com.quxianggif.network.request.FetchVCodeRequest
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus
import java.lang.Exception
import java.util.*
import java.util.regex.Pattern

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/22 下午5:55
 * Describe:开源版界面登录，支持手机号登录，如果登陆的账号没有注册就会跳转到注册界面如果已经注册过了就直接会跳转到主界面
 */
class OpenSourceLoginActivity :LoginActivity(){

    companion object {
        const val TAG="OpenSourceLoginActivity"
    }

    private lateinit var timer:CountDownTimer

    //是否正在登录中
    private var isLogin=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun setupViews() {
        super.setupViews()
        val isStartWithTransition=intent.getBooleanExtra(LoginActivity.START_WITH_TRANSITION,false)
        if(AndroidVersion.hasLollipop()&&isStartWithTransition){
            isTransitioning=true
            window.sharedElementEnterTransition.addListener(object : SimpleTransitionListener() {
                override fun onTransitionEnd(transition: Transition?) {
                    val event=FinishActivityEvent()
                    event.activityClass=OpenSourceSplashActivity::class.java
                    EventBus.getDefault().post(event)
                    isTransitioning=false
                    fadeElementsIn()
                }
            })
        }else{
            loginLayoutBottom.visibility= View.VISIBLE
            loginBgWallLayout.visibility=View.VISIBLE
        }

        timer=SMSTimer(60*1000,1000)
        getVerifyCode.setOnClickListener {
            getVerifyCodeAction()
        }

        loginButton.setOnClickListener {
            getLoginAction()
        }

    }

    //获取验证码
    private fun getVerifyCodeAction(){
        val number=phoneNumberEdit.text.toString()
        if(number.isEmpty()){
            showToast(GlobalUtil.getString(R.string.phone_number_is_empty))
            return
        }

        if(!isLegalPhone(number)){
            return
        }

        getVerifyCode.isClickable=false
        processGetVerifyCode(number)
    }

    //登录操作
    private fun getLoginAction(){
        if(isLogin) return
        val number=phoneNumberEdit.text.toString()
        val code=verifyCodeEdit.text.toString()
        if(number.isEmpty()||code.isEmpty()){
            showToast(GlobalUtil.getString(R.string.phone_number_or_code_is_empty))
            return
        }

        if(!isLegalPhone(number)){
            return
        }
        processLogin(number,code)
    }

    //验证手机号是否合法
    private fun isLegalPhone(number:String):Boolean{
        val pattern="^1\\d{10}\$"
        if(!Pattern.matches(pattern,number)){
            showToast(GlobalUtil.getString(R.string.phone_number_is_invalid))
            return false
        }
        return true
    }

    //将LoginActivity的界面元素使用淡入的方式显示出来
    private fun fadeElementsIn(){
        TransitionManager.beginDelayedTransition(loginLayoutBottom, Fade())
        loginLayoutBottom.visibility = View.VISIBLE
        TransitionManager.beginDelayedTransition(loginBgWallLayout,Fade())
        loginBgWallLayout.visibility = View.VISIBLE
    }

    //开始获取验证码
    private fun processGetVerifyCode(number: String){
        FetchVCode.getResponse(number,object :Callback{
            override fun onResponse(response: Response) {
                if(response.status==0){
                    timer.start()
                    verifyCodeEdit.requestFocus()
                }else{
                    showToast(response.msg)
                    getVerifyCode.isClickable=true
                }
            }

            override fun onFailure(e: Exception) {
                logWarn(TAG,e.message,e)
                ResponseHandler.handleFailure(e)
                getVerifyCode.isClickable=true
            }

        })
    }

    //开始登陆
    private fun processLogin(number: String,code:String){
        hideSoftKeyboard()
        loginInProgress(true)
        PhoneLogin.getResponse(number,code,object:Callback{
            override fun onResponse(response: Response) {
                if(!ResponseHandler.handleResponse(response)){
                    val thirdPartyLogin=response as PhoneLogin
                    val status=thirdPartyLogin.status
                    val msg=thirdPartyLogin.msg
                    val userId=thirdPartyLogin.userId
                    val token=thirdPartyLogin.token
                    when (status){
                        0->{
                            hideSoftKeyboard()
                            //处理登录成功时的逻辑，包括数据缓存，界面跳转等
                            saveAuthData(userId,token, TYPE_PHONE_LOGIN)
                            getUserBaseInfo()
                        }
                        10101->{
                            hideSoftKeyboard()

                        }
                    }
                }
            }

            override fun onFailure(e: Exception) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    //根据用户是否正在注册来刷新页面。如果正在处理就显示进度条，否则的话就显示输入框
    private fun loginInProgress(inProgress:Boolean){
        if(AndroidVersion.hasMarshmallow()&&!(inProgress&&loginRootLayout.keyboardShowed)){
            TransitionManager.beginDelayedTransition(loginRootLayout,Fade())
        }
        isLogin=inProgress
        if (inProgress) {
            loginInputElements.visibility = View.INVISIBLE
            loginProgressBar.visibility = View.VISIBLE
        } else {
            loginProgressBar.visibility = View.INVISIBLE
            loginInputElements.visibility = View.VISIBLE
        }
    }

    override fun forwardToMainActivity() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class SMSTimer(millisInFuture:Long,countDownInterval:Long):CountDownTimer(millisInFuture,countDownInterval){

        override fun onFinish() {
            getVerifyCode.text="获取验证码"
            getVerifyCode.isClickable=true
        }

        override fun onTick(millisUntilFinished: Long) {
            getVerifyCode.text=String.format(GlobalUtil.getString(R.string.sms_is_sent),millisUntilFinished/1000)
        }

    }
}