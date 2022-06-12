package com.example.dalangapp.content

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dalangapp.MainActivity
import com.example.dalangapp.databinding.ActivityMoreKnowledgeBinding

class MoreKnowledgeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoreKnowledgeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoreKnowledgeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.iconDalang.setOnClickListener {
            val intent = Intent(this, DalangBioActivity::class.java)
            startActivity(intent)
        }

        binding.iconVideo.setOnClickListener {
            val intent = Intent(this, VideoActivity::class.java)
            startActivity(intent)
        }

        binding.iconStories.setOnClickListener {
            val intent = Intent(this, StoriesActivity::class.java)
            startActivity(intent)
        }

        binding.iconMuseum.setOnClickListener {
            val intent = Intent(this, StudioMuseumActivity::class.java)
            startActivity(intent)
        }

        binding.iconShop.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
        }

        binding.btnBackToHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.iconVideo.setOnClickListener {
            val intent = Intent(this, VideoActivity::class.java)
            startActivity(intent)
        }


    }
}