package com.example.dalangapp.content

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dalangapp.MainActivity
import com.example.dalangapp.adapter.DalangAdapter
import com.example.dalangapp.content.detail.DetailActivity
import com.example.dalangapp.databinding.ActivityDalangBioBinding
import com.example.dalangapp.retrofit.ApiConfig
import com.example.dalangapp.retrofit.responses.DalangResponse
import com.example.dalangapp.retrofit.responses.DataItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DalangBioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDalangBioBinding
    private lateinit var adapter: DalangAdapter

    private lateinit var sharedPreferences: SharedPreferences
    val listDalangItem = ArrayList<DataItem>()

    private var SHARED_PREF_NAME = "mypref"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDalangBioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)

        setDalang()
        initRv()

        binding.btnBackToHome.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

    }

    private fun setDalang() {
        showLoading(true)
        val service = ApiConfig.getApiService(this).getDalang()
        service.enqueue(object : Callback<DalangResponse> {
            override fun onResponse(
                call: Call<DalangResponse>,
                response: Response<DalangResponse>
            ) {
                if (response.isSuccessful) {
                    showLoading(false)
                    val responseBody = response.body()
                    if (responseBody != null) {
                        response.body()?.data?.let { listDalangItem.addAll(it) }
                        adapter.setList(listDalangItem)
                        adapter.setList(responseBody.data)

                    } else {
                        showLoading(false)
                        Toast.makeText(
                            this@DalangBioActivity,
                            "Sorry Data Cannot Loaded",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<DalangResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(
                    this@DalangBioActivity,
                    "Connection Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun initRv() {
        adapter = DalangAdapter()
        binding.apply {
            rvDalang.layoutManager = LinearLayoutManager(this@DalangBioActivity)
            rvDalang.setHasFixedSize(true)
            rvDalang.adapter = adapter

            adapter.setOnItemClickCallback(object : DalangAdapter.OnItemClickCallback {
                override fun onItemClicked(data: DataItem) {
                    Intent(this@DalangBioActivity, DetailActivity::class.java).also {
                        it.putExtra(DetailActivity.EXTRA_PHOTO, data.url)
                        it.putExtra(DetailActivity.EXTRA_NAME, data.name)
                        it.putExtra(DetailActivity.EXTRA_DESK, data.biography)
                        it.putExtra(
                            DetailActivity.EXTRA_DESK1,
                            "Asal/Tempat Lahir : " + data.origin
                        )
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