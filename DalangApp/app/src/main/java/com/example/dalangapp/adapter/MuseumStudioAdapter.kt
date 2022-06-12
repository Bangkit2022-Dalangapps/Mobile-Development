package com.example.dalangapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.dalangapp.databinding.ItemMuseumCeritaVideoBinding
import com.example.dalangapp.retrofit.responses.ListMuseumStudioItems

class MuseumStudioAdapter : RecyclerView.Adapter<MuseumStudioAdapter.MuseumStudioViewHolder>() {

    private val list = ArrayList<ListMuseumStudioItems>()
    private var onItemClickCallback: OnItemClickCallback? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(places: ArrayList<ListMuseumStudioItems>) {
        list.clear()
        list.addAll(places)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class MuseumStudioViewHolder(private val binding: ItemMuseumCeritaVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(places: ListMuseumStudioItems) {
            binding.apply {
                Glide.with(itemView)
                    .load(places.url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .fitCenter()
                    .into(imItemMcv)
                tvItemTitleMcv.text = places.name
                tvItemDeskMcv.text = places.location
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MuseumStudioViewHolder {
        val view =
            ItemMuseumCeritaVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MuseumStudioViewHolder(view)
    }

    override fun onBindViewHolder(holder: MuseumStudioViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListMuseumStudioItems)
    }
}