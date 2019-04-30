package com.example.main.feeds.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.MenuItem
import com.example.core.Const
import com.example.core.GifFun
import com.example.core.extension.logDebug
import com.example.core.util.GlobalUtil
import com.example.core.util.ShareUtil
import com.example.main.R
import com.example.main.ui.BaseActivity
import org.litepal.LitePal
import org.litepal.LitePalDB

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/11 下午6:27
 * Describe:
 */
class MainActivity :BaseActivity(), NavigationView.OnNavigationItemSelectedListener{

    private lateinit var pagerAdapter:Adapter;

    internal var isNeedToRefresh=false

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //检查是否刷新主页
    private fun checkIsNeedToRefresh(){
        val autoRefresh=PreferenceManager.getDefaultSharedPreferences(this).getBoolean(GlobalUtil.getString(R.string.key_auto_refresh),true)
        if(autoRefresh){
            val lastUseTime=ShareUtil.read(Const.Feed.MAIN_LAST_USE_TIME,0L)
            val timeNotUsed=System.currentTimeMillis()-lastUseTime
            logDebug(TAG,"not used for " + timeNotUsed / 1000 + " seconds")
            if(timeNotUsed>10*60*1000){//超过10分钟未使用
                isNeedToRefresh=true
            }
        }

    }

    //初始化数据库
    private fun initDatabase(){
        val litepalDB=LitePalDB.fromDefault("giffun_"+GifFun.getUserId().toString())
        LitePal.use(litepalDB)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkIsNeedToRefresh()
        initDatabase()
        setContentView(R.layout.activity_main)
    }

    override fun setupViews() {
        setupToolbar()
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    private fun setupViewPager(viewPager: ViewPager){
        pagerAdapter= Adapter(supportFragmentManager)
        pagerAdapter.addFragment()
    }

    internal class Adapter(fm:FragmentManager):FragmentPagerAdapter(fm){
        private val mFragments=ArrayList<Fragment>()
        private val mFragmentTitles=ArrayList<String>()

        fun addFragment(fragment: Fragment,title:String){
            mFragments.add(fragment)
            mFragmentTitles.add(title)
        }

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitles[position]
        }

    }

    companion object {

        private const val TAG="MainActivity"

        private const val REQUEST_SEARCH=10000

        fun actionStart(activity: Activity){
            val intent= Intent(activity,MainActivity::class.java)
            activity.startActivity(intent)
        }
    }
}