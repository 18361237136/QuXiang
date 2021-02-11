package com.example.main.login.ui

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.transition.Fade
import android.transition.TransitionManager
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.example.core.util.AndroidVersion
import com.example.core.util.GlobalUtil
import com.example.main.R
import com.example.main.event.FinishActivityEvent
import com.example.main.feeds.ui.MainActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.greenrobot.eventbus.EventBus

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

    //判断用户昵称是否合法。用户昵称长度必须在2-30个字符之间，并且只能包含中英文，数字，下划线和横线
    val isNicknameValid:Boolean
        get() {
            val nickname=nicknameEdit.text.toString()
            if(nickname.length<2){
                nicknameInputLayout.isErrorEnabled = true
                nicknameInputLayout.error = GlobalUtil.getString(R.string.nickname_length_invalid)
                return false
            }else if(!nickname.matches(NICK_NAME_REG_EXP.toRegex())){
                nicknameInputLayout.isErrorEnabled=true
                nicknameInputLayout.error=GlobalUtil.getString(R.string.nickname_invalid)
                return false
            }
            return true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    //获取Intent中传递过来的数据并显示到界面上
    override fun setupViews() {
        setupToolbar()
        nicknameEditText=nicknameEdit
        nicknameLayout=nicknameInputLayout
        title=""//注册界面的Toolbar不需要Title
        moveOnText.setOnClickListener { doRegister() }
        nicknameEdit.setOnEditorActionListener(this)
        loginType=intent.getIntExtra(INTENT_LOGIN_TYPE,0)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if(actionId==EditorInfo.IME_ACTION_GO){
            doRegister()
        }
        return false
    }

    //根据用户是否正在注册来刷新页面。如果正在处理就显示进度条，否则的话就显示输入框
    protected fun registerInProgress(inProgress:Boolean){
        if(AndroidVersion.hasMarshmallow()){
            TransitionManager.beginDelayedTransition(registerRootLayout, Fade())
        }
        isRegistering=inProgress
        if(inProgress){
            moveOnText.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            nicknameInputLayout.visibility = View.GONE
        }else{
            moveOnText.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            nicknameInputLayout.visibility = View.VISIBLE
        }
    }

    override fun forwardToMainActivity() {
        MainActivity.actionStart(this)
        val event=FinishActivityEvent()
        event.activityClass=LoginActivity::class.java
        EventBus.getDefault().post(event)
        finish()
    }

    //开始执行注册逻辑
    abstract fun doRegister()

    companion object{
        private const val TAG="RegisterActivity"

        const val INTENT_OPEN_ID = "intent_open_id"

        const val INTENT_ACCESS_TOKEN = "intent_access_token"

        const val INTENT_NICKNAME = "intent_nickname"

        const val INTENT_PHONE_NUMBER = "intent_phone_number"

        const val INTENT_VERIFY_CODE = "intent_verify_code"

        const val INTENT_LOGIN_TYPE = "intent_login_type"

        /**
         * 检查用户昵称是否合法的正式表达式。
         */
        const val NICK_NAME_REG_EXP = "^[\u4E00-\u9FA5A-Za-z0-9_\\-]+$"
    }

}