package com.example.main.common.adapter

import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.bumptech.glide.Glide
import com.example.bumptech.glide.Priority
import com.example.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bumptech.glide.load.resource.drawable.GlideDrawable
import com.example.bumptech.glide.request.RequestListener
import com.example.bumptech.glide.request.target.Target
import com.example.core.GifFun
import com.example.core.extension.dp2px
import com.example.core.extension.logWarn
import com.example.core.extension.showToast
import com.example.core.model.BaseFeed
import com.example.core.model.FollowingFeed
import com.example.core.model.SimpleListFeed
import com.example.core.model.WorldFeed
import com.example.core.util.AndroidVersion
import com.example.core.util.GlobalUtil
import com.example.main.R
import com.example.main.common.holder.LoadingMoreViewHolder
import com.example.main.common.transitions.TransitionUtils
import com.example.main.common.view.CheckableImageButton
import com.example.main.event.DeleteFeedEvent
import com.example.main.feeds.adapter.FollowingFeedAdapter
import com.example.main.feeds.ui.FeedDetailActivity
import com.example.main.user.adapter.UserFeedAdapter
import com.example.main.user.ui.UserHomePageActivity
import com.example.main.util.DateUtil
import com.example.main.util.ViewUtils
import com.example.main.util.glide.CustomUrl
import com.quxianggif.network.model.Callback
import com.quxianggif.network.model.DeleteFeed
import com.quxianggif.network.model.Response
import jp.wasbeef.glide.transformations.CropCircleTransformation
import org.greenrobot.eventbus.EventBus
import org.litepal.LitePal
import org.litepal.extension.deleteAllAsync


/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/13 下午5:08
 * Describe:单列列表Feed数据显示的适配器。
 */
abstract class SimpleListFeedAdapter<T:SimpleListFeed,A:Activity>(protected open var activity:A,protected val feedList:MutableList<T>,private val maxImageWidth: Int,
                                                                  private val layoutManager: RecyclerView.LayoutManager?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val maxImageHeight:Int= dp2px(250f)

    private var imageWidth: Int = 0

    private var imageHeight: Int = 0

    val dataItemCount:Int
        get() = feedList.size

    abstract var isNoMoreData: Boolean

    abstract var isLoadFailed: Boolean

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            TYPE_FEEDS->return createFeedHolder(parent)
            TYPE_REFEEDS -> return createRefeedHolder(parent)
            TYPE_LOADING_MORE -> return createLoadingMoreHolder(parent)
        }
        throw IllegalArgumentException()
    }

    protected fun initBaseFeedHolder(holder: SimpleListFeedViewHolder){
        holder.cardView.setOnClickListener{v->
            val position=holder.adapterPosition
            if (AndroidVersion.hasLollipopMR1()) {
                setFabTransition(holder.feedCover)
            }
            val feed:BaseFeed?
            val simpleListFeed = feedList[position]
            feed=if(simpleListFeed.feedType==1){
                simpleListFeed.refFeed()
            }else{
                simpleListFeed
            }
            if(feed==null){
                showToast("原分享已被删除")
                return@setOnClickListener
            }else if(feed.coverLoadFailed){
                loadFeedCover(feed,holder)
            }else if(!feed.coverLoaded){
                return@setOnClickListener
            }
            val coverImage=v.findViewById<ImageView>(R.id.feedCover)
            FeedDetailActivity.actionStart(activity,coverImage,feed)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FeedViewHolder -> bindFeedHolder(holder, position)
            is RefeedViewHolder -> bindRefeedHolder(holder, position)
            is LoadingMoreViewHolder -> bindLoadingMoreHolder(holder)
        }
    }

    protected open fun bindFeedHolder(holder: FeedViewHolder, position: Int) {
        val feed = feedList[position]
        bindFeedCover(holder, feed)
        bindFeedBasicInfo(holder, feed)
    }

    protected open fun bindRefeedHolder(holder: RefeedViewHolder, position: Int) {
        val feed = feedList[position]
        val refFeed = feed.refFeed()
        if (refFeed != null) {
            holder.refFeedContent.visibility = View.VISIBLE
            holder.feedCover.visibility = View.VISIBLE
            holder.refFeedDeleted.visibility = View.GONE
            holder.refFeedContent.text = getRefFeedContent(refFeed)
            holder.refFeedContent.movementMethod = LinkMovementMethod.getInstance()
            bindFeedCover(holder, refFeed)
        } else {
            holder.refFeedContent.visibility = View.GONE
            holder.feedCover.visibility = View.GONE
            holder.refFeedDeleted.visibility = View.VISIBLE
        }
        bindFeedBasicInfo(holder, feed)
    }

    private fun getRefFeedContent(refFeed: BaseFeed): SpannableString {
        val nickname = refFeed.nickname
        val spanString = SpannableString(nickname + ": " + refFeed.content)
        spanString.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                UserHomePageActivity.actionStart(activity, null, refFeed.userId, refFeed.nickname, refFeed.avatar, refFeed.bgImage)
            }

            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.isUnderlineText = false //去除超链接的下划线
            }
        }, 0, nickname.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spanString.setSpan(ForegroundColorSpan(ContextCompat.getColor(activity, R.color.refeed_nickname)),
                0, nickname.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        return spanString
    }

    /**
     * 加载Feed的封面。
     */
    private fun bindFeedCover(holder: SimpleListFeedViewHolder, feed: BaseFeed) {
        calculateImageHeight(feed)
        holder.feedCover.layoutParams.width = imageWidth
        holder.feedCover.layoutParams.height = imageHeight
        loadFeedCover(feed, holder)
    }

    private fun calculateImageHeight(feed: BaseFeed) {
        val originalWidth = feed.imgWidth
        val originalHeight = feed.imgHeight
        imageWidth = maxImageWidth
        imageHeight = imageWidth * originalHeight / originalWidth
        if (imageHeight > maxImageHeight) {
            imageHeight = maxImageHeight
            imageWidth = imageHeight * originalWidth / originalHeight
        }
    }

    private fun bindLoadingMoreHolder(holder: LoadingMoreViewHolder) {
        when {
            isNoMoreData -> {
                holder.progress.visibility = View.GONE
                holder.failed.visibility = View.GONE
                holder.end.visibility = View.VISIBLE
            }
            isLoadFailed -> {
                holder.progress.visibility = View.GONE
                holder.failed.visibility = View.VISIBLE
                holder.end.visibility = View.GONE
            }
            else -> {
                holder.progress.visibility = View.VISIBLE
                holder.failed.visibility = View.GONE
                holder.end.visibility = View.GONE
            }
        }
    }

    /**
     * 加载Feed的基础数据信息。
     */
    private fun bindFeedBasicInfo(holder: SimpleListFeedViewHolder, feed: SimpleListFeed) {
        holder.nickname.text = feed.nickname
        holder.feedContent.text = feed.content
        holder.postDate.text = DateUtil.getConvertedDate(feed.postDate)
        holder.likesCount.text = feed.likesCount.toString()

        if (AndroidVersion.hasLollipop()) {
            val imageButton = holder.likes as CheckableImageButton
            imageButton.isChecked = feed.isLikedAlready
        } else {
            if (feed.isLikedAlready) {
                holder.likes.setImageResource(R.drawable.ic_liked)
            } else {
                holder.likes.setImageResource(R.drawable.ic_like)
            }
        }
        if (feed.avatar.isBlank()) {
            Glide.with(activity)
                    .load(R.drawable.avatar_default)
                    .bitmapTransform(CropCircleTransformation(activity))
                    .placeholder(R.drawable.loading_bg_circle)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.avatar)
        } else {
            Glide.with(activity)
                    .load(CustomUrl(feed.avatar))
                    .bitmapTransform(CropCircleTransformation(activity))
                    .placeholder(R.drawable.loading_bg_circle)
                    .error(R.drawable.avatar_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.avatar)
        }

        if (layoutManager != null) {
            val visibleItemCount = layoutManager.childCount
            if (visibleItemCount >= dataItemCount - 1) {
                onLoad()
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setFabTransition(item: View) {
        val fab = activity.findViewById<View>(R.id.composeFab)
        if (!ViewUtils.viewsIntersect(item, fab)) return

        val reenter = TransitionInflater.from(activity)
                .inflateTransition(R.transition.compose_fab_reenter)
        reenter.addListener(object : TransitionUtils.TransitionListenerAdapter() {
            override fun onTransitionEnd(transition: Transition) {
                activity.window.reenterTransition = null
            }
        })
        activity.window.reenterTransition = reenter
    }

    private fun loadFeedCover(feed: BaseFeed, holder: SimpleListFeedViewHolder) {
        Glide.with(activity)
                .load(feed.cover)
                .override(feed.imgWidth, feed.imgHeight)
                .placeholder(R.drawable.loading_bg_rect)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .priority(Priority.IMMEDIATE)
                .listener(object : RequestListener<String, GlideDrawable> {
                    override fun onException(e: java.lang.Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
                        feed.coverLoaded = false
                        feed.coverLoadFailed = true
                        return false
                    }

                    override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        feed.coverLoaded = true
                        feed.coverLoadFailed = false
                        return false
                    }

                })
                .into(holder.feedCover)
    }

    abstract fun onLoad()

    abstract fun createFeedHolder(parent: ViewGroup):FeedViewHolder

    abstract fun createRefeedHolder(parent: ViewGroup): RefeedViewHolder

    class FeedViewHolder(view: View):SimpleListFeedViewHolder(view)

    class RefeedViewHolder(view: View) : SimpleListFeedViewHolder(view) {

        internal val refFeedContent: TextView = view.findViewById(R.id.refFeedContent)

        internal val refFeedDeleted: TextView = view.findViewById(R.id.refFeedDeleted)

    }

    override fun getItemCount(): Int {
        return dataItemCount+1
    }

    override fun getItemViewType(position: Int): Int {
        if (position < dataItemCount && dataItemCount > 0) {
            val feed = feedList[position]
            return if (feed.feedType == 1) { // 转发类型
                TYPE_REFEEDS
            } else {
                TYPE_FEEDS
            }
        }
        return TYPE_LOADING_MORE
    }

    //获取扩展菜单中的子项，如果是自己所发的Feed就显示删除项，否则不显示删除项。
    private fun getExpandMenuItems(feed:SimpleListFeed):List<String>{
        val expandMenuItems=ArrayList<String>()
        expandMenuItems.add("举报")
        if(feed.userId==GifFun.getUserId()){
            expandMenuItems.add("删除")
        }
        return expandMenuItems;
    }

    //执行删除Feed的逻辑。
    private fun doDeleteAction(position:Int,feedId:Long){
        val builder=AlertDialog.Builder(activity,R.style.GifFunAlertDialogStyle)
        builder.setMessage(GlobalUtil.getString(R.string.delete_feed_confirm))
        builder.setPositiveButton(GlobalUtil.getString(R.string.ok)){_,_->
            DeleteFeed.getResponse(feedId,object :Callback{
                override fun onResponse(response: Response) {
                    if (response.status == 0) {
                        // 删除成功，发出删除事件，以更新相关界面数据。
                        val deleteFeedEvent = DeleteFeedEvent()
                        deleteFeedEvent.feedId = feedId
                        if (this@SimpleListFeedAdapter is UserFeedAdapter) {
                            deleteFeedEvent.type = DeleteFeedEvent.DELETE_FROM_USER_HOME_PAGE
                        } else if (this@SimpleListFeedAdapter is FollowingFeedAdapter) {
                            deleteFeedEvent.type = DeleteFeedEvent.DELETE_FROM_FOLLOWING_PAGE
                        }
                        EventBus.getDefault().post(deleteFeedEvent)
                    } else {
                        showToast(GlobalUtil.getString(R.string.delete_failed))
                        logWarn(TAG, "Delete feed failed. " + GlobalUtil.getResponseClue(response.status, response.msg))

                    }
                }

                override fun onFailure(e: Exception) {
                    logWarn(TAG, e.message, e)
                    showToast(GlobalUtil.getString(R.string.delete_failed))
                }

            })
            feedList.removeAt(position)
            notifyItemRemoved(position)
            LitePal.deleteAllAsync<WorldFeed>("feedid = ?", feedId.toString()).listen(null)
            LitePal.deleteAllAsync<FollowingFeed>("feedid = ?", feedId.toString()).listen(null)
        }
        builder.setNegativeButton(GlobalUtil.getString(R.string.cancel), null)
        builder.create().show()
    }

    private fun createLoadingMoreHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val holder = LoadingMoreViewHolder.createLoadingMoreViewHolder(activity, parent)
        holder.failed.setOnClickListener {
            onLoad()
            notifyItemChanged(itemCount - 1)
        }
        return holder
    }

    open class SimpleListFeedViewHolder internal constructor(view: View):RecyclerView.ViewHolder(view){

        val cardView: CardView = view as CardView

        val avatar: ImageView = view.findViewById(R.id.avatar)

        val nickname: TextView = view.findViewById(R.id.nickname)

        val postDate: TextView = view.findViewById(R.id.postDate)

        val expandButton: ImageView = view.findViewById(R.id.expandButton)

        val feedCover: ImageView = view.findViewById(R.id.feedCover)

        val feedContent: TextView = view.findViewById(R.id.feedContent)

        val likesCount: TextView = view.findViewById(R.id.likesCount)

        val likes: ImageView = view.findViewById(R.id.likes)

        val likesLayout: FrameLayout = view.findViewById(R.id.likesLayout)

        val repostLayout: FrameLayout = view.findViewById(R.id.repostLayout)

        val commentLayout: FrameLayout = view.findViewById(R.id.commentLayout)
    }

    companion object {

        private const val TAG = "SimpleListFeedAdapter"

        private const val TYPE_FEEDS = 0

        private const val TYPE_REFEEDS = 2

        private const val TYPE_LOADING_MORE = 1
    }
}