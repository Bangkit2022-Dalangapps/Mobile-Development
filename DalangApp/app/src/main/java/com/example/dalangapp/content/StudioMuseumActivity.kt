package com.example.dalangapp.content

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dalangapp.MainActivity
import com.example.dalangapp.adapter.MuseumStudioAdapter
import com.example.dalangapp.content.detail.DetailActivity
import com.example.dalangapp.databinding.ActivityStudioMuseumBinding
import com.example.dalangapp.retrofit.ApiConfig
import com.example.dalangapp.retrofit.responses.ListMuseumStudioItems
import com.example.dalangapp.retrofit.responses.MuseumStudioResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudioMuseumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudioMuseumBinding
    private lateinit var adapter: MuseumStudioAdapter

    private lateinit var sharedPreferences: SharedPreferences
    val listStoryItem = ArrayList<ListMuseumStudioItems>()

    private var SHARED_PREF_NAME = "mypref"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudioMuseumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)

        setMuseum()
        initRv()

        binding.btnBackToHome.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }

    private fun setMuseum() {
        showLoading(true)
        val service = ApiConfig.getApiService(this).getMuseumStudio()
        service.enqueue(object : Callback<MuseumStudioResponse> {
            override fun onResponse(
                call: Call<MuseumStudioResponse>,
                response: Response<MuseumStudioResponse>
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
                            this@StudioMuseumActivity,
                            "Sorry Data Cannot Loaded",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<MuseumStudioResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(
                    this@StudioMuseumActivity,
                    "Connection Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun initRv() {
        adapter = MuseumStudioAdapter()
        binding.apply {
            rvMcv.layoutManager = LinearLayoutManager(this@StudioMuseumActivity)
            rvMcv.setHasFixedSize(true)
            rvMcv.adapter = adapter


            adapter.setOnItemClickCallback(object : MuseumStudioAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ListMuseumStudioItems) {
                    Intent(this@StudioMuseumActivity, DetailActivity::class.java).also {
                        it.putExtra(DetailActivity.EXTRA_PHOTO, data.url)
                        it.putExtra(DetailActivity.EXTRA_NAME, data.name)
                        it.putExtra(DetailActivity.EXTRA_DESK, data.description)
                        it.putExtra(DetailActivity.EXTRA_DESK1, "Lokasi : " + data.location)
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