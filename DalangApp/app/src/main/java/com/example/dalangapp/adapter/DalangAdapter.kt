package com.example.dalangapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.dalangapp.databinding.ItemDalangBinding
import com.example.dalangapp.retrofit.responses.DataItem

class DalangAdapter : RecyclerView.Adapter<DalangAdapter.DalangViewHolder>() {

    private val list = ArrayList<DataItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(dalang: ArrayList<DataItem>) {
        list.clear()
        list.addAll(dalang)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class DalangViewHolder(private val binding: ItemDalangBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dalang: DataItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(dalang.url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .into(imgItemPhoto)
                tvItemName.text = dalang.name
                tvItemDesk.text = dalang.origin
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DalangViewHolder {
        val view = ItemDalangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DalangViewHolder(view)
    }

    override fun onBindViewHolder(holder: DalangViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DataItem)
    }
}