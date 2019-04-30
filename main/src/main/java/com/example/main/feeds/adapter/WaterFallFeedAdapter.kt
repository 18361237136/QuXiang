package com.example.main.feeds.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.core.model.WaterFallFeed

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/30 下午5:20
 * Describe:瀑布流列表Feed数据显示的适配器
 */
abstract class WaterFallFeedAdapter<T:WaterFallFeed>(protected var activity:Activity,private val feedList: List<T>, private val imageWidth: Int,
                                                     private val layoutManager: RecyclerView.LayoutManager?):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //获取RecyclerView数据源中元素的数量
    private val dataItemCount:Int
        get() = feedList.size

    abstract var isNoMoreData:Boolean

    abstract var isLoadFailed:Boolean

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){

        }
    }

    open class FeedViewHolder(view: View):RecyclerView.ViewHolder(view){
        
    }

    companion object{
        private const val TAG="WaterFallFeedAdapter"

        const val TYPE_FEEDS = 0

        private const val TYPE_LOADING_MORE = 1
    }
}