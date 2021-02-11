package com.example.main.feeds.ui

import android.os.Bundle
import android.support.v4.widget.CursorAdapter
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import com.example.core.GifFun
import com.example.core.extension.postDelayed
import com.example.main.R
import com.example.main.common.callback.InfiniteScrollListener
import com.example.main.common.callback.LoadDataListener
import com.example.main.common.callback.PendingRunnable
import com.example.main.event.CleanCacheEvent
import com.example.main.event.MessageEvent
import com.example.main.event.RefreshMainActivityFeedsEvent
import com.example.main.ui.BaseFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/30 上午10:59
 * Describe:应用程序中所有Feed相关Fragment的基类
 */
abstract class BaseFeedsFragment :BaseFragment() {

    //判断是否正在加载更多Feeds
    internal var isLoadingMore=false

    lateinit var activity:MainActivity

    lateinit var swipeRefresh:SwipeRefreshLayout

    lateinit var recyclerView: RecyclerView

    internal lateinit var adapter: RecyclerView.Adapter<*>

    internal lateinit var loadDataListener:LoadDataListener

    internal lateinit var layoutManager:RecyclerView.LayoutManager

    var pendingRunnable=SparseArray<PendingRunnable>()

    var isLoadFailed:Boolean=false

    var isNoMoreData=false
        internal set

    internal fun initViews(rootView: View) {
        recyclerView = rootView.findViewById(R.id.recyclerView)
        swipeRefresh = rootView.findViewById(R.id.swipeRefresh)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadDataListener=this as LoadDataListener
        activity=getActivity() as MainActivity
        setupRecyclerView()
        swipeRefresh.setColorSchemeResources(R.color.colorAccent)
        recyclerView.addOnScrollListener(object:InfiniteScrollListener(layoutManager){
            override fun onLoadMore() {
                loadDataListener.onLoad()
            }

            override fun isDataLoading()=isLoadingMore

            override fun isNoMoreData()=isNoMoreData

        })
        swipeRefresh.setOnRefreshListener { refreshFeeds() }
        loadFeedsFromDB()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(messageEvent: MessageEvent) {
        if (messageEvent is RefreshMainActivityFeedsEvent) {
            // 只要当加载失败的情况下，收到RefreshMainActivityFeedsEvent才会执行刷新，否则不进行刷新
            if(isLoadFailed){
                GifFun.getHandler().postDelayed(300){// 略微进行延迟处理，使界面上可以看到波纹动画效果
                    reloadFeeds()
                }
            }
        } else if (messageEvent is CleanCacheEvent) {
            reloadFeeds()
        }
    }

    //重新加载feeds，在加载过程中如果界面上没有元素则显示ProgressBar，如果界面上已经有元素则显示SwipeRefresh。
    private fun reloadFeeds(){
        if(adapter.itemCount<=1){
            startLoading()
        }else{
            swipeRefresh.isRefreshing=true
        }
        refreshFeeds()
    }

    //加载feeds完成，将feeds显示出来，将加载等待控件隐藏。
    override fun loadFinished() {
        super.loadFinished()
        isLoadFailed=false
        recyclerView.visibility = View.VISIBLE
        swipeRefresh.visibility = View.VISIBLE
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }

    //加载feeds失败，将加载等待控件隐藏。
    override fun loadFailed(msg: String?) {
        super.loadFailed(msg)
        isLoadFailed = true
        swipeRefresh.isRefreshing = false
        if(dataSetSize()==0){
            if(msg==null){
                swipeRefresh.visibility = View.GONE
                showBadNetworkView(View.OnClickListener {
                    val event = RefreshMainActivityFeedsEvent()
                    EventBus.getDefault().post(event)
                })
            }else{
                showLoadErrorView(msg)
            }
        }else{
            adapter.notifyItemChanged(adapter.itemCount-1)
        }
    }

    //执行潜在的Pending任务
    fun executePendingRunnableList(){
        val size=pendingRunnable.size()
        if(size>0){
            for(i in 0 until size){
                val index=pendingRunnable.keyAt(i)
                val runnable=pendingRunnable.get(index)
                runnable.run(index)
            }
            pendingRunnable.clear()
        }
    }

    //将RecyclerView滚动到顶部
    fun scrollToTop(){
        if(adapter.itemCount!=0){
            recyclerView.smoothScrollToPosition(0)
        }
    }

    internal abstract fun setupRecyclerView()

    internal abstract fun loadFeeds(lastFeed: Long)

    internal abstract fun refreshFeeds()

    internal abstract fun loadFeedsFromDB()

    internal abstract fun dataSetSize(): Int

    companion object{
        private const val TAG = "BaseFeedsFragment"
    }
}