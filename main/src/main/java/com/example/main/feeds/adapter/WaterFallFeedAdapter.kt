package com.example.main.feeds.adapter

import android.app.Activity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.core.model.WaterFallFeed
import com.example.main.R

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

    abstract fun createFeedHolder(parent: ViewGroup): FeedViewHolder

    abstract fun bindFeedHolder(holder: FeedViewHolder, position: Int)

    open class FeedViewHolder(view: View):RecyclerView.ViewHolder(view){
        val cardView: CardView = view as CardView

        val feedCover: ImageView = view.findViewById(R.id.feedCover)

        val feedContent: TextView = view.findViewById(R.id.feedContent)

        val avatar: ImageView = view.findViewById(R.id.avatar)

        val nickname: TextView = view.findViewById(R.id.nickname)

        val likes: ImageView = view.findViewById(R.id.likes)

        val likesCount: TextView = view.findViewById(R.id.likesCount)

        val likesLayout: LinearLayout = view.findViewById(R.id.likesLayout)
    }

    companion object{
        private const val TAG="WaterFallFeedAdapter"

        const val TYPE_FEEDS = 0

        private const val TYPE_LOADING_MORE = 1
    }
}