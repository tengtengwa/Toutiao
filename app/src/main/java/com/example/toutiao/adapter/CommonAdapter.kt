package com.example.toutiao.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.toutiao.logic.model.NewsModel

class CommonAdapter(private val resId: Int, private val list: List<NewsModel.NewsList>) :
        RecyclerView.Adapter<CommonAdapter.Item1Holder>() {

    companion object {
        private const val ITEM_STYLE_1 = 1
        private const val ITEM_STYLE_2 = 2
        private const val ITEM_STYLE_3 = 3
    }

    class Item1Holder(viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        var binding: ProlistItemBinding = viewDataBinding as ProlistItemBinding
    }

    class Item2Holder(viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        var binding: ProlistItemBinding = viewDataBinding as ProlistItemBinding
    }

    class Item3Holder(viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        var binding: ProlistItemBinding = viewDataBinding as ProlistItemBinding
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        ITEM_STYLE_1 -> {
            val binding = DataBindingUtil.inflate<>(LayoutInflater.from(parent.context),
                    resId, parent, false)
            Item1Holder(binding)
        }
        ITEM_STYLE_2 -> {
        }
        ITEM_STYLE_3 -> {
        }
        else -> {
        }
    }

    /*: Item1Holder {
        val binding = DataBindingUtil.inflate<ProlistItemBinding>(LayoutInflater.from(parent.context),
                resId, parent, false)
        return Item1Holder(binding)
    }*/

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int): Int = when (position) {
        1, 2 -> ITEM_STYLE_1
        else -> if (list[position].images.size >= 3) ITEM_STYLE_3 else ITEM_STYLE_2     //根据照片多少加载相应布局
    }

    override fun onBindViewHolder(holder: Item1Holder, position: Int) {
        val item = list[position]
        holder.binding.listitem = item

        holder.itemView.setOnClickListener {
            onListItemClicked.onItemClick(holder.itemView, position)
        }

    }

    private lateinit var onListItemClicked: OnListItemClicked

    interface OnListItemClicked {
        fun onItemClick(view: View, pos: Int)
    }
}