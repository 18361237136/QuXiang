package com.example.main.feeds.ui

import android.app.Activity
import android.content.Intent
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import com.example.main.feeds.model.Draft
import com.example.main.ui.BaseActivity

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/12 下午5:00
 * Describe:编辑和分享GIF图的界面，用户可以在这里选择和预览要分享的GIF图片，以及为图片编辑说明文字，然后分享出去。
 */
class PostFeedActivity : BaseActivity(), View.OnClickListener, TextView.OnEditorActionListener{

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        return false
    }

    override fun onClick(v: View?) {

    }

    companion object {

        private const val TAG = "PostFeedActivity"

        private const val INTENT_DRAFT = "intent_draft"

        private const val PICK_GIF = 1

        fun actionStart(activity: Activity) {
            val intent = Intent(activity, PostFeedActivity::class.java)
            activity.startActivity(intent)
        }

        fun actionStart(activity: Activity, draft: Draft) {
            val intent = Intent(activity, PostFeedActivity::class.java)
            intent.putExtra(INTENT_DRAFT, draft)
            activity.startActivity(intent)
        }
    }
}