package com.example.dalangapp.content

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dalangapp.MainActivity
import com.example.dalangapp.databinding.ActivityLearnWayangBinding


class LearnWayangActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLearnWayangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnWayangBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnBackToHome.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }
}