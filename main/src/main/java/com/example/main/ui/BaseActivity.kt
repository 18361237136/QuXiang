package com.example.main.ui

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ProgressBar
import com.example.main.R
import com.example.main.common.callback.PermissionListener
import com.example.main.util.ActivityCollector
import com.umeng.analytics.MobclickAgent
import kotlinx.android.synthetic.main.activity_main.view.*
import org.greenrobot.eventbus.EventBus
import java.lang.ref.WeakReference

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/8 上午11:24
 * Describe:Activity的基类
 */
open class BaseActivity :AppCompatActivity() {

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

}

