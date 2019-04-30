package com.example.main.feeds.ui

import com.example.core.model.WorldFeed
import com.example.main.common.callback.LoadDataListener

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/30 上午10:54
 * Describe:展示界面频道的Feeds内容
 */
class WorldFeedsFragment :WaterFallFeedsFragment(),LoadDataListener{

    //RecyclerView的数据源用于存储所有展示中的Feeds.
    internal var feedList:MutableList<WorldFeed> =ArrayList()

    override fun setupRecyclerView() {
        super.setupRecyclerView()
        adapter=
    }

    override fun loadFeeds(lastFeed: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshFeeds() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadFeedsFromDB() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dataSetSize(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        private const val TAG = "WorldFeedsFragment"
    }

}