package com.example.main.common.holder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.main.R

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/5/6 上午10:34
 * Describe:用于在RecyclerView当中显示加载更多的进度条
 */
class LoadingMoreViewHolder private constructor(view: View):RecyclerView.ViewHolder(view){

    val progress: ProgressBar = view.findViewById(R.id.loadProgress)

    val end: ImageView = view.findViewById(R.id.loadingEnd)

    val failed: TextView = view.findViewById(R.id.loadFailed)

    companion object{
        fun createLoadingMoreViewHolder(context: Context, parent: ViewGroup): LoadingMoreViewHolder {
            val view= LayoutInflater.from(context).inflate(R.layout.loading_footer, parent, false)
            return LoadingMoreViewHolder(view)
        }
    }
}