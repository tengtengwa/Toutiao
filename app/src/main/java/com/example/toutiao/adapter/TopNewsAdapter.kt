package com.example.toutiao.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.toutiao.R
import com.example.toutiao.logic.model.TopNewsModel
import com.example.toutiao.utils.ImageUtil

class TopNewsAdapter(private val topNews: ArrayList<TopNewsModel.TopNewsList>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_STYLE_1 = 1
        private const val ITEM_STYLE_2 = 2
        private const val ITEM_STYLE_3 = 3
    }

    class Item1Holder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tv_item1_title)
        val author: TextView = view.findViewById(R.id.tv_item1_author)
        val time: TextView = view.findViewById(R.id.tv_top)
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
        ITEM_STYLE_1 -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_top, parent, false)
            Item1Holder(view)
        }
        ITEM_STYLE_2 -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_1image, parent, false)
            Item2Holder(view)
        }
        else -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_3images, parent, false)
            Item3Holder(view)
        }
    }

    override fun getItemCount() = topNews.size

    override fun getItemViewType(position: Int): Int {
        if (position == 0 || position == 1) {
            return ITEM_STYLE_1
        } else if (topNews[position].time != null) {
            return if (topNews[position].tmp3pic != null) ITEM_STYLE_3 else ITEM_STYLE_2
        }
        return ITEM_STYLE_1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemNewsInfo = topNews[position]
        when (holder) {
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
        topNews.removeAt(pos)
        notifyItemRemoved(pos)
    }

    lateinit var onListItemClicked: OnListItemClicked

    interface OnListItemClicked {
        fun onItemClick(view: View, pos: Int)
    }
}