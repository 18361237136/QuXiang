package com.example.main.user.ui

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.widget.ImageView
import com.example.core.util.AndroidVersion
import com.example.core.util.GlobalUtil
import com.example.main.R
import com.example.main.ui.BaseActivity

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/7/19 下午2:54
 * Describe:浏览用户大图
 */
class BrowserPhotoActivity :BaseActivity(){

    companion object{
        private const val TAG = "BrowserPhotoActivity"

        private const val URL = "url"

        fun actionStart(activity: Activity,url:String,imageView:ImageView){
            val intent=Intent(activity,BrowserPhotoActivity::class.java)
            intent.putExtra(URL,url)
            if(AndroidVersion.hasLollipop()){
                val options=ActivityOptions.makeSceneTransitionAnimation(activity,imageView, GlobalUtil.getString(R.string.transition_browse_photo))
                activity.startActivity(intent,options.toBundle())
            }else{
                activity.startActivity(intent)
            }
        }
    }
}