package com.example.toutiao.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.toutiao.R
import com.example.toutiao.base.BaseApplication
import com.example.toutiao.logic.model.AntipNewsModel
import com.example.toutiao.ui.activity.DetailsActivity
import com.example.toutiao.utils.ImageUtil
import com.example.toutiao.utils.NetworkUtil
import com.example.toutiao.utils.toast
import kotlinx.android.synthetic.main.antip_web_item.*
import kotlinx.android.synthetic.main.web_view_details.*

class AntipNewsAdapter(private val antipNews: ArrayList<AntipNewsModel.AntipNewsList>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ANTIP_WEB_ITEM = 0
        private const val ITEM_STYLE_1 = 1
        private const val ITEM_STYLE_2 = 2
        private const val ITEM_STYLE_3 = 3
    }

    class AntipItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        val web: WebView = view.findViewById(R.id.web_anti_condition)
        val more: ImageView = view.findViewById(R.id.iv_antip_more)
    }

    class Item1Holder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tv_item1_title)
        val author: TextView = view.findViewById(R.id.tv_item1_author)
        val time: TextView = view.findViewById(R.id.tv_item1_time)
        val removeItem1: ImageView = view.findViewById(R.id.iv_remove_item1)
    }

    class Item2Holder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tv_item2_title)
        val author: TextView = view.findViewById(R.id.tv_item2_author)
        val time: TextView = view.findViewById(R.id.tv_item2_time)
        val image: ImageView = view.findViewById(R.id.iv_item2_image)
        val removeItem2: ImageView = view.findViewById(R.id.iv_remove_item2)
    }

    class Item3Holder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tv_item3_title)
        val author: TextView = view.findViewById(R.id.tv_item3_author)
        val time: TextView = view.findViewById(R.id.tv_item3_time)
        val image1: ImageView = view.findViewById(R.id.iv_item3_image1)
        val image2: ImageView = view.findViewById(R.id.iv_item3_image2)
        val image3: ImageView = view.findViewById(R.id.iv_item3_image3)
        val removeItem3: ImageView = view.findViewById(R.id.iv_remove_item3)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        ANTIP_WEB_ITEM-> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.antip_web_item, parent, false)
            AntipItemHolder(view)
        }
        ITEM_STYLE_1 -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item1, parent, false)
            Item1Holder(view)
        }
        ITEM_STYLE_2 -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item2, parent, false)
            Item2Holder(view)
        }
        else -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item3, parent, false)
            Item3Holder(view)
        }
    }

    override fun getItemCount() = antipNews.size

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return ANTIP_WEB_ITEM
        } else if (antipNews[position].time != null) {
            return if (antipNews[position].tmp3pic != null) ITEM_STYLE_3 else ITEM_STYLE_2
        }
        return ITEM_STYLE_1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemNewsInfo = antipNews[position]
        when (holder) {
            is AntipItemHolder-> {
                val context = BaseApplication.context
                if (NetworkUtil.isNetworkConnected(context)) {
                    holder.web.settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        setPluginState(WebSettings.PluginState.ON)
                        allowFileAccess = true
                        loadWithOverviewMode = true
                        useWideViewPort = true
                        databaseEnabled = true
                        layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
                        defaultTextEncodingName = "UTF-8"
                        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                    }
                    holder.web.apply {
                        settings.javaScriptEnabled = true
                        settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
                        webChromeClient = WebChromeClient()
                        loadUrl("https://news.qq.com/zt2020/page/antip.htm?channelId=news_news_antip")
                    }
                    holder.more.setOnClickListener {
                        val intent = Intent(context, DetailsActivity::class.java)
                        intent.putExtra("url", "https://news.qq.com/zt2020/page/feiyan.htm#/global")
                        context.startActivity(intent)
                    }
                } else {
                    "加载失败，请检查网络设置".toast(context)
                }
            }
            is Item1Holder -> {
                holder.apply {
                    title.text = itemNewsInfo.title
                    author.text = itemNewsInfo.source
                    time.text = itemNewsInfo.time
                }
                holder.itemView.setOnClickListener {
                    onListItemClicked.onItemClick(holder.itemView, position)
                }
                holder.removeItem1.setOnClickListener {
                    removeItem(position)
                }
            }
            is Item2Holder -> {
                holder.apply {
                    title.text = itemNewsInfo.title
                    author.text = itemNewsInfo.source
                    time.text = itemNewsInfo.time
                    if (itemNewsInfo.image != null && itemNewsInfo.image.isNotEmpty()) {
                        ImageUtil.GlideLoadImage(image, itemNewsInfo.image[0])
                    }
                }
                holder.itemView.setOnClickListener {
                    onListItemClicked.onItemClick(holder.itemView, position)
                }
                holder.removeItem2.setOnClickListener {
                    removeItem(position)
                }
            }
            is Item3Holder -> {
                holder.apply {
                    title.text = itemNewsInfo.title
                    author.text = itemNewsInfo.source
                    time.text = itemNewsInfo.time
                    ImageUtil.GlideLoadImage(image1, itemNewsInfo.tmp3pic[0])
                    ImageUtil.GlideLoadImage(image2, itemNewsInfo.tmp3pic[1])
                    ImageUtil.GlideLoadImage(image3, itemNewsInfo.tmp3pic[2])
                }
                holder.itemView.setOnClickListener {
                    onListItemClicked.onItemClick(holder.itemView, position)
                }
                holder.removeItem3.setOnClickListener {
                    removeItem(position)
                }
            }
        }
    }

    private fun removeItem(pos: Int) {
        antipNews.removeAt(pos)
        notifyItemRemoved(pos)
    }

    lateinit var onListItemClicked: OnListItemClicked

    interface OnListItemClicked {
        fun onItemClick(view: View, pos: Int)
    }
}