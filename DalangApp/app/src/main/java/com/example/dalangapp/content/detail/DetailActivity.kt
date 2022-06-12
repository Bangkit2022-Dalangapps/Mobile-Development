package com.example.dalangapp.content.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.dalangapp.MainActivity
import com.example.dalangapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_PHOTO = "extra_photo"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_DESK = "extra_desk"
        const val EXTRA_DESK1 = "extra_desk1"
        const val EXTRA_DESK2 = "extra_desk2"
        const val EXTRA_DESK3 = "extra_desk3"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoUrl = intent.getStringExtra(EXTRA_PHOTO)
        val name = intent.getStringExtra(EXTRA_NAME)
        val desk = intent.getStringExtra(EXTRA_DESK)
        val desk1 = intent.getStringExtra(EXTRA_DESK1)
        val desk2 = intent.getStringExtra(EXTRA_DESK2)
        val desk3 = intent.getStringExtra(EXTRA_DESK3)

        Glide.with(this)
            .load(photoUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .fitCenter()
            .into(binding.ivDetail)
        binding.tvJudulDetail.text = name
        binding.tvDeskripsiDetail.text = desk
        binding.tvOtherInfoDesc1.text = desk1
        binding.tvOtherInfoDesc2.text = desk2
        binding.tvOtherInfoDesc3.text = desk3

        binding.btnBackToHome.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

    }

}