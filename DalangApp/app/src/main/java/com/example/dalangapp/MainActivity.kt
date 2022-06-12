package com.example.dalangapp

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.dalangapp.adapter.ImageSliderAdapter
import com.example.dalangapp.content.*
import com.example.dalangapp.databinding.ActivityMainBinding
import com.example.dalangapp.loginregis.LoginActivity
import com.example.dalangapp.wayangcamera.WayangCameraActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ImageSliderAdapter

    private val listSlide = ArrayList<ImageData>()
    private lateinit var dots: ArrayList<TextView>
    private lateinit var sharedPreferences: SharedPreferences

    private var SHARED_PREF_NAME = "mypref"


    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        const val EXTRA_OPTION = "extra_option"

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)

        when (intent.getStringExtra(EXTRA_OPTION)) {
            "logout" -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        listSlide.add(ImageData(R.drawable.slide1))
        listSlide.add(ImageData(R.drawable.slide2))
        listSlide.add(ImageData(R.drawable.slide3))
        listSlide.add(ImageData(R.drawable.slide4))

        adapter = ImageSliderAdapter(listSlide)
        binding.viewPager.adapter = adapter
        dots = ArrayList()
        setIndicator()
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                selectedDot(position)
                super.onPageSelected(position)
            }
        })

        binding.viewPager.setOnClickListener {
            val i = Intent(this, WayangGalleryActivity::class.java)
            startActivity(i)
        }

        binding.ivGallery.setOnClickListener {
            val i = Intent(this, WayangGalleryActivity::class.java)
            startActivity(i)
        }

        binding.ivBioDalang.setOnClickListener {
            val i = Intent(this, DalangBioActivity::class.java)
            startActivity(i)
        }

        binding.ivMuseumStudio.setOnClickListener {
            val i = Intent(this, StudioMuseumActivity::class.java)
            startActivity(i)
        }

        binding.ivWayangStories.setOnClickListener {
            val i = Intent(this, StoriesActivity::class.java)
            startActivity(i)
        }

        binding.tvShowMoreGallery.setOnClickListener {
            val i = Intent(this, WayangGalleryActivity::class.java)
            startActivity(i)
        }

        binding.btnStartLearingWayang.setOnClickListener {
            val i = Intent(this, LearnWayangActivity::class.java)
            startActivity(i)
        }

        binding.btnScanFromCamera.setOnClickListener {
            Intent(this, WayangCameraActivity::class.java).also {
                it.putExtra(WayangCameraActivity.EXTRA_OPTION, "camera")
                startActivity(it)
            }
        }

        binding.btnScanFromGallery.setOnClickListener {
            Intent(this, WayangCameraActivity::class.java).also {
                it.putExtra(WayangCameraActivity.EXTRA_OPTION, "gallery")
                startActivity(it)
            }
        }

        binding.btnSettings.setOnClickListener {
            val i = Intent(this, SettingActivity::class.java)
            startActivity(i)
        }

        binding.tvShowMore.setOnClickListener {
            val i = Intent(this, MoreKnowledgeActivity::class.java)
            startActivity(i)
        }

        binding.ivShowMore.setOnClickListener {
            val i = Intent(this, MoreKnowledgeActivity::class.java)
            startActivity(i)
        }

    }

    private fun selectedDot(position: Int) {
        for (i in 0 until listSlide.size) {
            if (i == position) {
                dots[i].setTextColor(ContextCompat.getColor(this, R.color.light_brown))
            } else {
                dots[i].setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))
            }
        }

    }

    private fun setIndicator() {
        for (i in 0 until listSlide.size) {
            dots.add(TextView(this))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dots[i].text = Html.fromHtml("&#9679", Html.FROM_HTML_MODE_LEGACY).toString()
            } else {
                dots[i].text = Html.fromHtml("&#9679")
            }
            dots[i].textSize = 15f
            binding.dotsIndicator.addView(dots[i])
        }
    }
}