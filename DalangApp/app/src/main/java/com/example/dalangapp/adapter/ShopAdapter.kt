package com.example.dalangapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.dalangapp.databinding.ItemMuseumCeritaVideoBinding
import com.example.dalangapp.retrofit.responses.ListShopItems

class ShopAdapter : RecyclerView.Adapter<ShopAdapter.ShopViewHolder>() {

    private val list = ArrayList<ListShopItems>()
    private var onItemClickCallback: OnItemClickCallback? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(shop: ArrayList<ListShopItems>) {
        list.clear()
        list.addAll(shop)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ShopViewHolder(private val binding: ItemMuseumCeritaVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListShopItems) {
            binding.apply {
                Glide.with(itemView)
                    .load(data.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .fitCenter()
                    .into(imItemMcv)
                tvItemTitleMcv.text = data.name
                tvItemDeskMcv.text = data.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val view =
            ItemMuseumCeritaVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListShopItems)
    }
}