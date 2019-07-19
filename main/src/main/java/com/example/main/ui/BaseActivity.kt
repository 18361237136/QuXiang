package com.example.main.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewStub
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.core.util.AndroidVersion
import com.example.main.R
import com.example.main.common.callback.PermissionListener
import com.example.main.common.callback.RequestLifecycle
import com.example.main.event.ForceToLoginEvent
import com.example.main.event.MessageEvent
import com.example.main.login.ui.LoginActivity
import com.example.main.util.ActivityCollector
import com.umeng.analytics.MobclickAgent
import kotlinx.android.synthetic.main.activity_main.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.ref.WeakReference

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/8 上午11:24
 * Describe:Activity的基类
 */
@SuppressLint("Registered")
open class BaseActivity :AppCompatActivity(),RequestLifecycle {

    //判断当前Activity是否在前台
    protected var isActive: Boolean = false

    //当前Activity
    protected var activity: Activity? = null

    //Activity中显示加载等待的控件
    protected var loading:ProgressBar?=null

    //Activity中于服务器异常导致加载失败显示的布局
    private var loadErrorView: View?=null

    //Activity中由于网络异常导致加载失败显示的布局。
    private var badNetworkView:View?=null

    // Activity中当界面上没有任何内容时展示的布局。
    private var noContentView:View?=null

    private var weakReActivity:WeakReference<Activity>?=null

    var toolbar: Toolbar?=null

    private var progressDialog:ProgressDialog?=null

    private var mListener:PermissionListener?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity=this
        weakReActivity= WeakReference(this)
        ActivityCollector.add(weakReActivity)
        EventBus.getDefault().register(this)
    }

    override fun onResume() {
        super.onResume()
        isActive=true
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        isActive=false
        MobclickAgent.onPause(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        activity=null
        ActivityCollector.remove(weakReActivity)
        EventBus.getDefault().unregister(this)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setupViews()
    }

    protected open fun setupViews(){
        loading=findViewById(R.id.loading)
    }

    protected fun setupToolbar(){
        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar=supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //将状态栏设置成透明，只适配5.0以上手机
    protected fun transparentStatusBar(){
        if(AndroidVersion.hasLollipop()){
            val decorView=window.decorView
            decorView.systemUiVisibility=View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.statusBarColor=Color.TRANSPARENT
        }
    }

    //检查和处理运行时权限，并将用户授权的结果通过PermissionListener进行回调
    protected fun handlePermissions(permissions:Array<String>?,listener: PermissionListener){
        if(permissions==null||activity==null){
            return
        }
        mListener=listener
        val requestPermissionList=ArrayList<String>()
        for(permission in permissions){
            if(ContextCompat.checkSelfPermission(activity!!,permission)!=PackageManager.PERMISSION_GRANTED){
                requestPermissionList.add(permission)
            }
        }
        if(!requestPermissionList.isEmpty()){
            ActivityCompat.requestPermissions(activity!!,requestPermissionList.toTypedArray(),1)
        }else{
            listener.onGranted()
        }
    }

    //隐藏软键盘
    fun hideSoftKeyboard(){
        val view=currentFocus
        if(view!=null) {
            val binder = view.windowToken
            val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(binder,InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    //显示软键盘
    fun showSoftKeyboard(editText:EditText?){
        if(editText!=null) {
            editText.requestFocus()
            val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.showSoftInput(editText, 0)
        }
    }

    //当服务器返回加载内容失败时，显示该界面
    protected fun showLoadErrorView(tip:String){
        if(loadErrorView!=null){
            loadErrorView?.visibility=View.VISIBLE
            return
        }
        val viewStub=findViewById<ViewStub>(R.id.loadErrorView)
        if(viewStub!=null){
            loadErrorView=viewStub.inflate()
            val loadErrorText=loadErrorView?.findViewById<TextView>(R.id.loadErrorText)
            loadErrorText?.text=tip
        }
    }

    //当因为网络原因无法显示的时候，显示该界面
    protected fun showBadNetworkView(listener:View.OnClickListener){
        if(badNetworkView!=null){
            badNetworkView?.visibility=View.VISIBLE
            return
        }
        val stubView=findViewById<ViewStub>(R.id.badNetworkView)
        if(stubView!=null) {
            badNetworkView=stubView.inflate()
            val badNetworkRootView=badNetworkView?.findViewById<View>(R.id.badNetworkRootView)
            badNetworkRootView?.setOnClickListener(listener)
        }
    }

    //当获取到的数据为空时，显示该界面
    protected fun showNoContentView(tip:String){
        if(noContentView!=null){
            noContentView?.visibility=View.VISIBLE
            return
        }
        val stubView=findViewById<ViewStub>(R.id.noContentView)
        if(stubView!=null) {
            noContentView=stubView.inflate()
            val noContentText=noContentView?.findViewById<TextView>(R.id.noContentText)
            noContentText?.text=tip
        }
    }

    //将noContentView隐藏
    protected fun hideNoContentView(){
        noContentView?.visibility=View.GONE
    }

    //将loadErrorView隐藏
    protected fun hideLoadErrorView(){
        loadErrorView?.visibility=View.GONE
    }

    //将badNetworkView隐藏
    protected fun hideBadNetworkView(){
        badNetworkView?.visibility=View.GONE
    }

    fun showProgressDialog(title:String?,message:String){
        if(progressDialog==null){
            progressDialog=ProgressDialog(this).apply {
                if(title!=null){
                    setTitle(title)
                }
                setMessage(message)
                setCancelable(false)
            }
        }
        progressDialog?.show()
    }

    fun closeProgressDialog(){
        progressDialog?.let {
            if(it.isShowing){
                it.dismiss()
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(messageEvent:MessageEvent){
        if(messageEvent is ForceToLoginEvent){
            if(isActive){//判断Activity是否在前台，防止非前台的Activity也处理这个事件，造成打开多个LoginActivity的问题。
                ActivityCollector.finishAll()
                LoginActivity.actionStart(this,false,null)
            }
        }
    }

    @CallSuper
    override fun startLoading() {
        loading?.visibility = View.VISIBLE
        hideBadNetworkView()
        hideNoContentView()
        hideLoadErrorView()
    }

    @CallSuper
    override fun loadFinished() {
        loading?.visibility = View.GONE
    }

    @CallSuper
    override fun loadFailed(msg: String?) {
        loading?.visibility = View.GONE
    }

    companion object {

        private const val TAG = "BaseActivity"
    }

}

