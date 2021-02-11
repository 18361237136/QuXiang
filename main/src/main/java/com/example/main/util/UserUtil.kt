package com.example.main.util

import com.example.core.Const
import com.example.core.util.ShareUtil

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/24 下午3:54
 * Describe:获取当前登录用户信息工具类
 */
object UserUtil {

    val nickname: String
        get() = ShareUtil.read(Const.User.NICKNAME, "")

    val avatar: String
        get() = ShareUtil.read(Const.User.AVATAR, "")

    val bgImage: String
        get() = ShareUtil.read(Const.User.BG_IMAGE, "")

    val description: String
        get() = ShareUtil.read(Const.User.DESCRIPTION, "")

    fun saveNickname(nickname:String?){
        if(nickname!=null&&nickname.isNotBlank()){
            ShareUtil.save(Const.User.NICKNAME,nickname)
        }else{
            ShareUtil.clear(Const.User.NICKNAME)
        }
    }

    fun saveAvatar(avatar: String?) {
        if (avatar != null && avatar.isNotBlank()) {
            ShareUtil.save(Const.User.AVATAR, avatar)
        } else {
            ShareUtil.clear(Const.User.AVATAR)
        }
    }

    fun saveBgImage(bgImage: String?) {
        if (bgImage != null && bgImage.isNotBlank()) {
            ShareUtil.save(Const.User.BG_IMAGE, bgImage)
        } else {
            ShareUtil.clear(Const.User.BG_IMAGE)
        }
    }

    fun saveDescription(description: String?) {
        if (description != null && description.isNotBlank()) {
            ShareUtil.save(Const.User.DESCRIPTION, description)
        } else {
            ShareUtil.clear(Const.User.DESCRIPTION)
        }
    }
}