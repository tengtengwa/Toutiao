package com.example.toutiao.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.toutiao.R
import com.example.toutiao.databinding.PlaceItemBinding
import com.example.toutiao.logic.model.Place

class PlaceAdapter(private val placeList: List<Place>) :
        RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    class PlaceViewHolder(viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        var binding: PlaceItemBinding = viewDataBinding as PlaceItemBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val binding = DataBindingUtil.inflate<PlaceItemBinding>(LayoutInflater.from(parent.context),
                R.layout.item_place, parent, false)
        return PlaceViewHolder(binding)
    }

    override fun getItemCount() = placeList.size

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val item = placeList[position]
        holder.binding.place = item

        holder.itemView.setOnClickListener {
            onListItemClicked.onItemClick(holder.itemView, position)
        }
    }

    lateinit var onListItemClicked: OnListItemClicked

    interface OnListItemClicked {
        fun onItemClick(view: View, pos: Int)
    }
}