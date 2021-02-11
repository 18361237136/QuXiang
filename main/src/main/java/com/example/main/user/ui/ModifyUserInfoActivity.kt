package com.example.main.user.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import com.example.main.ui.BaseActivity

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/12 下午5:35
 * Describe:用户修改个人信息的界面，包括昵称、个人简介、头像、背景等。
 */
class ModifyUserInfoActivity : BaseActivity(), View.OnClickListener{

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        private const val TAG = "ModifyUserInfoActivity"

        const val TAKE_AVATAR_PICTURE = 0

        const val TAKE_BG_IMAGE_PICTURE = 1

        const val TAKE_PHOTO = 1000

        const val CHOOSE_FROM_ALBUM = 1001

        const val EDIT_DESCRIPTION = "edit_description"

        const val TEMP_PHOTO = "taken_photo.jpg"

        fun actionStart(activity: Activity) {
            val intent = Intent(activity, ModifyUserInfoActivity::class.java)
            activity.startActivity(intent)
        }

        fun actionEditDescription(activity: Activity) {
            val intent = Intent(activity, ModifyUserInfoActivity::class.java)
            intent.putExtra(EDIT_DESCRIPTION, true)
            activity.startActivity(intent)
        }
    }
}