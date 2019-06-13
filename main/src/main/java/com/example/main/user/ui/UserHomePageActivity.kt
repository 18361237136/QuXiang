package com.example.main.user.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import com.example.main.common.callback.LoadDataListener
import com.example.main.ui.BaseActivity

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/12 下午2:25
 * Describe:用户个人主页的Activity，在这里展示用户的个人信息，以及用户所发的所有Feeds。
 */
class UserHomePageActivity : BaseActivity(), LoadDataListener {

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