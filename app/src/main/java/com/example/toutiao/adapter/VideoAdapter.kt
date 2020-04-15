package com.example.toutiao.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.toutiao.R
import com.example.toutiao.base.BaseApplication
import com.example.toutiao.databinding.NewsVideoItemBinding
import com.example.toutiao.logic.model.VideoModel
import com.example.toutiao.utils.ImageUtil
import com.example.toutiao.utils.NetworkUtil
import com.example.toutiao.utils.toast

class VideoAdapter(private val videoList: List<VideoModel.VideoList>) :
        RecyclerView.Adapter<VideoAdapter.MyViewHolder>() {

    class MyViewHolder(viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        var binding: NewsVideoItemBinding = viewDataBinding as NewsVideoItemBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DataBindingUtil.inflate<NewsVideoItemBinding>(LayoutInflater.from(parent.context),
                R.layout.news_video_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = videoList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = videoList[position]
        if (item.time == null) return
        holder.binding.newslist = item
        val context = BaseApplication.context

        holder.binding.videoWeb.settings.apply {
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
        holder.binding.videoWeb.apply {
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            webChromeClient = WebChromeClient()
            if (item.image != null && item.image.isNotEmpty()) {
                loadUrl(item.image[0])
            }
        }
        holder.binding.tvPlaycount.text = item.videoInfo.video.playcount.toString()
        ImageUtil.GlideLoadImage(holder.binding.headIcon, item.authorIcon)
        holder.binding.btnPlay.setOnClickListener {
            if (NetworkUtil.isNetworkConnected(context)) {
                holder.binding.videoWeb.loadUrl(item.videoInfo.video.playurl)
                holder.binding.btnPlay.visibility = View.INVISIBLE
            } else {
                "加载失败，请检查网络设置".toast(context)
            }
        }
    }
}