package com.example.core.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/11 下午4:59
 * Describe:版本更新的实体类封装，如果存在版本更新则会提供下面描述信息
 */
@Parcelize
class Version(@SerializedName("change_log") val changeLog:String,
              @SerializedName("is_force") val isForce: Boolean,
              val url: String,
              @SerializedName("version_name") val versionName: String,
              @SerializedName("version_code") val versionCode: Int,
              val channel: String):Parcelable