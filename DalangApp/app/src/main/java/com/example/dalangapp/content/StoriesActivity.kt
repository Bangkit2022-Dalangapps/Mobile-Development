package com.example.dalangapp.content

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dalangapp.MainActivity
import com.example.dalangapp.adapter.StoriesAdapter
import com.example.dalangapp.content.detail.DetailActivity
import com.example.dalangapp.databinding.ActivityStoriesBinding
import com.example.dalangapp.retrofit.ApiConfig
import com.example.dalangapp.retrofit.responses.ListStoryItems
import com.example.dalangapp.retrofit.responses.StoriesRespond
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoriesBinding
    private lateinit var adapter: StoriesAdapter

    private lateinit var sharedPreferences: SharedPreferences
    val listStoryItem = ArrayList<ListStoryItems>()

    private var SHARED_PREF_NAME = "mypref"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)

        setStory()
        initRv()

        binding.btnBackToHome.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }

    private fun setStory() {
        showLoading(true)
        val service = ApiConfig.getApiService(this).getStories()
        service.enqueue(object : Callback<StoriesRespond> {
            override fun onResponse(
                call: Call<StoriesRespond>,
                response: Response<StoriesRespond>
            ) {
                if (response.isSuccessful) {
                    showLoading(false)
                    val responseBody = response.body()
                    if (responseBody != null) {
                        response.body()?.data?.let { listStoryItem.addAll(it) }
                        adapter.setList(listStoryItem)
                        adapter.setList(responseBody.data)

                    } else {
                        showLoading(false)
                        Toast.makeText(
                            this@StoriesActivity,
                            "Sorry Data Cannot Loaded",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<StoriesRespond>, t: Throwable) {
                showLoading(false)
                Toast.makeText(
                    this@StoriesActivity,
                    "Connection Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun initRv() {
        adapter = StoriesAdapter()
        binding.apply {
            rvMcv.layoutManager = LinearLayoutManager(this@StoriesActivity)
            rvMcv.setHasFixedSize(true)
            rvMcv.adapter = adapter

            adapter.setOnItemClickCallback(object : StoriesAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ListStoryItems) {
                    Intent(this@StoriesActivity, DetailActivity::class.java).also {
                        it.putExtra(DetailActivity.EXTRA_PHOTO, data.url)
                        it.putExtra(DetailActivity.EXTRA_NAME, data.title)
                        it.putExtra(DetailActivity.EXTRA_DESK, data.description)
                        it.putExtra(DetailActivity.EXTRA_DESK1, "Currently unavailable")
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