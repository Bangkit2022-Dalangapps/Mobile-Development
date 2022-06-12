package com.example.dalangapp.content

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dalangapp.MainActivity
import com.example.dalangapp.R
import com.example.dalangapp.adapter.ShopAdapter
import com.example.dalangapp.adapter.StoriesAdapter
import com.example.dalangapp.content.detail.DetailActivity
import com.example.dalangapp.databinding.ActivityShopBinding
import com.example.dalangapp.databinding.ActivityStoriesBinding
import com.example.dalangapp.retrofit.ApiConfig
import com.example.dalangapp.retrofit.responses.ListShopItems
import com.example.dalangapp.retrofit.responses.ListStoryItems
import com.example.dalangapp.retrofit.responses.ShopResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShopActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopBinding
    private lateinit var adapter : ShopAdapter

    private lateinit var sharedPreferences: SharedPreferences
    val listShopItems = ArrayList<ListShopItems>()

    private var SHARED_PREF_NAME = "mypref"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)

        setShop()
        initRv()

        binding.btnBackToHome.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }

    private fun setShop(){
        showLoading(true)
        val service = ApiConfig.getApiService(this).getShop()
        service.enqueue(object : Callback<ShopResponse>{
            override fun onResponse(call: Call<ShopResponse>, response: Response<ShopResponse>) {
                if (response.isSuccessful) {
                    showLoading(false)
                    val responseBody = response.body()
                    if (responseBody != null) {
                        response.body()?.data?.let { listShopItems.addAll(it) }
                        adapter.setList(listShopItems)
                        adapter.setList(responseBody.data)
                    } else {
                        showLoading(false)
                        Toast.makeText(
                            this@ShopActivity,
                            "Sorry Data Cannot Loaded",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<ShopResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(
                    this@ShopActivity,
                    "Connection Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun initRv(){
        adapter = ShopAdapter()
        binding.apply {
            rvMcv.layoutManager = LinearLayoutManager(this@ShopActivity)
            rvMcv.setHasFixedSize(true)
            rvMcv.adapter = adapter

            adapter.setOnItemClickCallback(object : ShopAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ListShopItems) {
                    Intent(this@ShopActivity, DetailActivity::class.java).also {
                        it.putExtra(DetailActivity.EXTRA_PHOTO, data.photoUrl)
                        it.putExtra(DetailActivity.EXTRA_NAME, data.name)
                        it.putExtra(DetailActivity.EXTRA_DESK, data.description)
                        it.putExtra(DetailActivity.EXTRA_DESK1, "Link Pembelian : "+data.linkUrl)
                        startActivity(it)
                    }
                }
            })
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}