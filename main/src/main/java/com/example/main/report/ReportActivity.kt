package com.example.main.report

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.main.ui.BaseActivity

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/7/19 下午3:31
 * Describe:用户对Feed、评论、用户进行举报的Activity。
 */
class ReportActivity :BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object{
        private const val INTENT_REPORT_TYPE = "intent_report_type"
        private const val INTENT_FEED_ID = "intent_feed_id"
        private const val INTENT_COMMENT_ID = "intent_comment_id"
        private const val INTENT_USER_ID = "intent_user_id"

        private const val REPORT_FEED = 0

        private const val REPORT_COMMENT = 1

        private const val REPORT_USER = 2

        fun actionReportFeed(context: Context, feedId: Long) {
            val intent = Intent(context, ReportActivity::class.java)
            intent.putExtra(INTENT_REPORT_TYPE, REPORT_FEED)
            intent.putExtra(INTENT_FEED_ID, feedId)
            context.startActivity(intent)
        }

        fun actionReportComment(context: Context, commentId: Long) {
            val intent = Intent(context, ReportActivity::class.java)
            intent.putExtra(INTENT_REPORT_TYPE, REPORT_COMMENT)
            intent.putExtra(INTENT_COMMENT_ID, commentId)
            context.startActivity(intent)
        }

        fun actionReportUser(context: Context, userId: Long) {
            val intent = Intent(context, ReportActivity::class.java)
            intent.putExtra(INTENT_REPORT_TYPE, REPORT_USER)
            intent.putExtra(INTENT_USER_ID, userId)
            context.startActivity(intent)
        }
    }
}