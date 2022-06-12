package com.example.dalangapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.dalangapp.databinding.ItemMuseumCeritaVideoBinding
import com.example.dalangapp.retrofit.responses.ListStoryItems

class StoriesAdapter : RecyclerView.Adapter<StoriesAdapter.McvViewHolder>() {

    private val list = ArrayList<ListStoryItems>()
    private var onItemClickCallback: OnItemClickCallback? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(data: ArrayList<ListStoryItems>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class McvViewHolder(private val binding: ItemMuseumCeritaVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStoryItems) {
            binding.apply {
                Glide.with(itemView)
                    .load(data.url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .fitCenter()
                    .into(imItemMcv)
                tvItemTitleMcv.text = data.title
                tvItemDeskMcv.text = data.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): McvViewHolder {
        val view =
            ItemMuseumCeritaVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return McvViewHolder(view)
    }

    override fun onBindViewHolder(holder: McvViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListStoryItems)
    }
}