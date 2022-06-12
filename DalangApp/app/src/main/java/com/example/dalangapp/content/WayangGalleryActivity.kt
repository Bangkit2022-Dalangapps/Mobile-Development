package com.example.dalangapp.content

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dalangapp.MainActivity
import com.example.dalangapp.content.detail.WayangDetailActivity
import com.example.dalangapp.databinding.ActivityWayangGalleryBinding

class WayangGalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWayangGalleryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWayangGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBeber.setOnClickListener {
            Intent(this, WayangDetailActivity::class.java).also {
                it.putExtra(WayangDetailActivity.EXTRA_ID, "1")
                startActivity(it)
            }
        }

        binding.ivGedog.setOnClickListener {
            Intent(this, WayangDetailActivity::class.java).also {
                it.putExtra(WayangDetailActivity.EXTRA_ID, "2")
                startActivity(it)
            }
        }

        binding.ivKulit.setOnClickListener {
            Intent(this, WayangDetailActivity::class.java).also {
                it.putExtra(WayangDetailActivity.EXTRA_ID, "5")
                startActivity(it)
            }
        }

        binding.ivSuluh.setOnClickListener {
            Intent(this, WayangDetailActivity::class.java).also {
                it.putExtra(WayangDetailActivity.EXTRA_ID, "3")
                startActivity(it)
            }
        }

        binding.ivGolek.setOnClickListener {
            Intent(this, WayangDetailActivity::class.java).also {
                it.putExtra(WayangDetailActivity.EXTRA_ID, "4")
                startActivity(it)
            }
        }

        binding.ivKrucil.setOnClickListener {
            Intent(this, WayangDetailActivity::class.java).also {
                it.putExtra(WayangDetailActivity.EXTRA_ID, "6")
                startActivity(it)
            }
        }

        binding.btnBackToHome.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

    }
}