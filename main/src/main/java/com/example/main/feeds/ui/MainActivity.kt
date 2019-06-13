package com.example.main.feeds.ui

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.graphics.Palette
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.bumptech.glide.Glide
import com.example.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bumptech.glide.load.resource.drawable.GlideDrawable
import com.example.bumptech.glide.request.RequestListener
import com.example.bumptech.glide.request.target.Target
import com.example.core.Const
import com.example.core.GifFun
import com.example.core.extension.dp2px
import com.example.core.extension.logDebug
import com.example.core.extension.toBitmap
import com.example.core.util.GlobalUtil
import com.example.core.util.ShareUtil
import com.example.main.R
import com.example.main.settings.ui.SettingsActivity
import com.example.main.ui.BaseActivity
import com.example.main.user.ui.ModifyUserInfoActivity
import com.example.main.user.ui.RecommendFollowingActivity
import com.example.main.user.ui.UserHomePageActivity
import com.example.main.util.AnimUtils
import com.example.main.util.ColorUtils
import com.example.main.util.UserUtil
import com.example.main.util.glide.CustomUrl
import jp.wasbeef.glide.transformations.BlurTransformation
import jp.wasbeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_main.*
import org.litepal.LitePal
import org.litepal.LitePalDB
import java.lang.Exception
import com.example.core.extension.*

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/11 下午6:27
 * Describe:
 */
class MainActivity :BaseActivity(), NavigationView.OnNavigationItemSelectedListener{

    private lateinit var pagerAdapter:Adapter;

    internal var isNeedToRefresh=false

    private var currentPagerPosition=0

    private lateinit var nicknameMe:TextView

    private lateinit var descriptionMe: TextView

    private lateinit var avatarMe: ImageView

    private lateinit var editImage: ImageView

    private var navHeaderBgLoadListener:RequestListener<Any,GlideDrawable> =object:RequestListener<Any, GlideDrawable>{
        override fun onException(e: Exception?, model: Any?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
            return false
        }

        override fun onResourceReady(glideDrawable: GlideDrawable?, model: Any?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
            if(glideDrawable==null){
                return false
            }
            val bitmap = glideDrawable.toBitmap()
            val bitmapWidth = bitmap.width
            val bitmapHeight = bitmap.height
            if (bitmapWidth <= 0 || bitmapHeight <= 0) {
                return false
            }
            val left = (bitmapWidth * 0.2).toInt()
            val right = bitmapWidth - left
            val top = bitmapHeight / 2
            val bottom = bitmapHeight - 1
            logDebug(TAG, "text area top $top , bottom $bottom , left $left , right $right")
            Palette.from(bitmap)
                    .maximumColorCount(3)
                    .clearFilters()
                    .setRegion(left, top, right, bottom) // 测量图片下半部分的颜色，以确定用户信息的颜色
                    .generate { palette ->
                        val isDark = ColorUtils.isBitmapDark(palette, bitmap)
                        val color: Int
                        color = if (isDark) {
                            ContextCompat.getColor(this@MainActivity, R.color.white_text)
                        } else {
                            ContextCompat.getColor(this@MainActivity, R.color.primary_text)
                        }
                        nicknameMe.setTextColor(color)
                        descriptionMe.setTextColor(color)
                        editImage.setColorFilter(color, android.graphics.PorterDuff.Mode.MULTIPLY)
                    }
            return false
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.compose -> GifFun.getHandler().postDelayed(300){ PostFeedActivity.actionStart(this) }
            R.id.user_home -> GifFun.getHandler().postDelayed(300) {
                UserHomePageActivity.actionStart(this, avatarMe, GifFun.getUserId(),
                        UserUtil.nickname, UserUtil.avatar, UserUtil.bgImage)
            }
            R.id.draft -> GifFun.getHandler().postDelayed(300) { DraftActivity.actionStart(this) }
            R.id.recommend_following -> GifFun.getHandler().postDelayed(300) { RecommendFollowingActivity.actionStart(this) }
            R.id.settings -> GifFun.getHandler().postDelayed(300) { SettingsActivity.actionStart(this) }
        }
        GifFun.getHandler().post {
            uncheckNavigationItems()
            drawerLayout.closeDrawers()
        }
        return true
    }

    private fun uncheckNavigationItems() {
        navView.setCheckedItem(R.id.none)
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
        setupViewPager(viewpager)
        tabs.setupWithViewPager(viewpager)
        tabs.addOnTabSelectedListener(tabSelectedListener)
        composeFab.setOnClickListener{
            PostFeedActivity.actionStart(this)
        }
        navView.setNavigationItemSelectedListener(this)
        popFab()
        animateToolbar()
        navView.viewTreeObserver.addOnPreDrawListener(object:ViewTreeObserver.OnPreDrawListener{
            override fun onPreDraw(): Boolean {
                navView.viewTreeObserver.removeOnPreDrawListener(this)
                loadUserInfo()
                return false
            }
        })
    }

    private fun setupViewPager(viewPager: ViewPager){
        pagerAdapter= Adapter(supportFragmentManager)
        pagerAdapter.addFragment(WorldFeedsFragment(),"世界")
        pagerAdapter.addFragment(WorldFeedsFragment(),"关注")
        pagerAdapter.addFragment(HotFeedsFragment(),"热门")
        viewPager.adapter=pagerAdapter
        viewPager.offscreenPageLimit=2
        currentPagerPosition=ShareUtil.read(Const.Feed.MAIN_PAGER_POSITION,0)
        if(currentPagerPosition<0||currentPagerPosition>=pagerAdapter.count){
            currentPagerPosition=0
        }
        viewPager.currentItem=currentPagerPosition
        viewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                currentPagerPosition=p0
                executePendingRunnable()
            }

        })

    }

    //执行Pending任务，用于同步ViewPager各面页签之间的状态。
    private fun executePendingRunnable(){
        val fragment=pagerAdapter.getItem(currentPagerPosition)
        if(fragment is BaseFeedsFragment){
            fragment.executePendingRunnableList()
        }
    }

    private val tabSelectedListener by lazy {
        object :TabLayout.ViewPagerOnTabSelectedListener(viewpager){
            override fun onTabReselected(tab: TabLayout.Tab?) {
                super.onTabReselected(tab)
                if(tab!=null){
                    val fragment=pagerAdapter.getItem(tab.position)
                    if(fragment is BaseFeedsFragment){
                        fragment.scrollToTop()
                    }
                }
                println("on tab onTabReselected ${tab?.position}")
            }
        }
    }

    //使用pop动画的方式将fab按钮显示出来。
    private fun popFab(){
        composeFab.show()
        composeFab.alpha=0f
        composeFab.scaleX=0f
        composeFab.scaleY=0f
        val animator=ObjectAnimator.ofPropertyValuesHolder(
                composeFab,
                PropertyValuesHolder.ofFloat(View.ALPHA,1f),
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f))
        animator.startDelay=200
        animator.start()
    }

    //使用缩放动画的方式将Toolbar标题显示出来。
    private fun animateToolbar(){
        val t=toolbar?.getChildAt(0)
        if(t!=null&&t is TextView){
            t.alpha=0f
            t.scaleX=0.8f
            t.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .setStartDelay(300)
                    .setDuration(900).interpolator=AnimUtils.getFastOutSlowInInterpolator(this)
        }
    }

    //加载登录用户的信息，头像和昵称等。
    private fun loadUserInfo(){
        val count=navView.headerCount
        if(count==1){
            val nickname = UserUtil.nickname
            val avatar = UserUtil.avatar
            val description = UserUtil.description
            val bgImage = UserUtil.bgImage
            val headerView = navView.getHeaderView(0)
            val userLayout = headerView.findViewById<LinearLayout>(R.id.userLayout)
            val descriptionLayout = headerView.findViewById<LinearLayout>(R.id.descriptionLayout)
            val navHeaderBg = headerView.findViewById<ImageView>(R.id.navHeaderBgImage)
            avatarMe = headerView.findViewById(R.id.avatarMe)
            nicknameMe = headerView.findViewById(R.id.nicknameMe)
            descriptionMe = headerView.findViewById(R.id.descriptionMe)
            editImage = headerView.findViewById(R.id.editImage)
            nicknameMe.text = nickname
            if (TextUtils.isEmpty(description)) {
                descriptionMe.text = GlobalUtil.getString(R.string.edit_description)
            } else {
                descriptionMe.text = String.format(GlobalUtil.getString(R.string.description_content), description)
            }
            Glide.with(this)
                    .load(CustomUrl(avatar))
                    .bitmapTransform(CropCircleTransformation(activity))
                    .placeholder(R.drawable.loading_bg_circle)
                    .error(R.drawable.avatar_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(avatarMe)
            if (TextUtils.isEmpty(bgImage)) {
                if (!TextUtils.isEmpty(avatar)) {
                    Glide.with(this)
                            .load(CustomUrl(avatar))
                            .bitmapTransform(BlurTransformation(this, 15))
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .listener(navHeaderBgLoadListener)
                            .into(navHeaderBg)
                }
            } else {
                val bgImageWidth = navView.width
                val bgImageHeight = dp2px((250 + 25).toFloat() /* 25为补偿系统状态栏高度，不加这个高度值图片顶部会出现状态栏的底色 */)
                Glide.with(this)
                        .load(bgImage)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .override(bgImageWidth, bgImageHeight)
                        .listener(navHeaderBgLoadListener)
                        .into(navHeaderBg)
            }
            userLayout.setOnClickListener { UserHomePageActivity.actionStart(this@MainActivity, avatarMe, GifFun.getUserId(), nickname, avatar, bgImage) }
            descriptionLayout.setOnClickListener { ModifyUserInfoActivity.actionEditDescription(this@MainActivity) }
        }
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