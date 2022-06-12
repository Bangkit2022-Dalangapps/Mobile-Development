package com.example.dalangapp.content

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dalangapp.MainActivity
import com.example.dalangapp.R
import com.example.dalangapp.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mediaController = android.widget.MediaController(this)
        mediaController.setAnchorView(binding.videoView1)

        val offlineUri: Uri = Uri.parse("android.resource://$packageName/${R.raw.video}")

        binding.apply {
            videoView1.setMediaController(mediaController)
            videoView1.setVideoURI(offlineUri)
            videoView1.requestFocus()
            videoView1.start()
        }

        binding.btnBackToHome.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

    }

}