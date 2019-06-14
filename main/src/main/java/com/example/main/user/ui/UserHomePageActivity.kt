package com.example.main.user.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import com.example.core.model.UserFeed
import com.example.main.common.callback.LoadDataListener
import com.example.main.ui.BaseActivity
import com.example.main.user.adapter.UserFeedAdapter

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/12 下午2:25
 * Describe:用户个人主页的Activity，在这里展示用户的个人信息，以及用户所发的所有Feeds。
 */
class UserHomePageActivity : BaseActivity(), LoadDataListener {

    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var adapter:UserFeedAdapter

    /**
     * RecyclerView的数据源，用于存储所有展示中的Feeds。
     */
    private lateinit var feedList: MutableList<UserFeed>

    /**
     * 当前主页用户的id。
     */
    private var mUserId: Long = 0

    /**
     * 当前主页用户的昵称。
     */
    var mNickname = ""

    /**
     * 当前主页用户的头像。
     */
    private var mAvatar = ""

    /**
     * 当前主页用户的个人简介。
     */
    private var mDescription = ""

    /**
     * 当前主页用户的背景图。
     */
    private var mBgImage = ""

    /**
     * 是否已经关注此用户。
     */
    private var isFollowed = false

    /**
     * 判断当前Fab按钮的关注状态
     */
    private var isFabFollowed = false

    private var isFollowInProgress = false

    private var isUserBgImageDark = false

    private var isToolbarAndStatusbarIconDark = false

    private var isUserBgImageLoaded = false

    var isNoMoreData=false
        private set

    var isLoadFailed=false

    //判断是否正在加载Fedds
    private var isLoading=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        private const val TAG = "UserHomePageActivity"

        const val USER_ID = "user_id"

        const val NICKNAME = "NICKNAME"

        const val AVATAR = "avatar"

        const val BG_IMAGE = "bg_image"

        fun actionStart(activity: Activity, image: View?, userId: Long, nickname: String, avatar: String, bgImage: String) {
            val intent = Intent(activity, UserHomePageActivity::class.java)
            intent.putExtra(USER_ID, userId)
            intent.putExtra(NICKNAME, nickname)
            intent.putExtra(AVATAR, avatar)
            intent.putExtra(BG_IMAGE, bgImage)
            activity.startActivity(intent)

            //        if (AndroidVersion.hasLollipop()) {
            //            ActivityOptions options =
            //                    ActivityOptions.makeSceneTransitionAnimation(activity,
            //                            Pair.create(image, GlobalUtil.getString(R.string.transition_user_home_page_avatar)));
            //
            //            activity.startActivity(intent, options.toBundle());
            //        } else {
            //            activity.startActivity(intent);
            //        }
        }
    }
}