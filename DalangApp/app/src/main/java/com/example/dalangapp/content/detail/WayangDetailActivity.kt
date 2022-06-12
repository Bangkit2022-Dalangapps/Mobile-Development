package com.example.dalangapp.content.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.dalangapp.MainActivity
import com.example.dalangapp.databinding.ActivityWayangDetailBinding
import com.example.dalangapp.retrofit.ApiConfig
import com.example.dalangapp.retrofit.responses.WayangData
import com.example.dalangapp.retrofit.responses.WayangResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WayangDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWayangDetailBinding
    val listWayangItems = ArrayList<WayangData>()

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWayangDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnBackToHome.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

        setWayang()
    }

    private fun setWayang(){
        showLoading(true)
        val id = intent.getStringExtra(EXTRA_ID)
        val service = id?.let { ApiConfig.getApiService(this).getWayang(it.toInt()) }
        service?.enqueue(object : Callback<WayangResponse>{
            override fun onResponse(
                call: Call<WayangResponse>,
                response: Response<WayangResponse>
            ) {
                if (response.isSuccessful) {
                    showLoading(false)
                    val responseBody = response.body()

                    if (responseBody != null) {
                        val dataBody = responseBody.data
                        response.body()?.data?.let { listWayangItems.addAll(listOf(it)) }
                        setData(dataBody.name,dataBody.description, dataBody.origin, dataBody.source,dataBody.url)

                    } else {
                        showLoading(false)
                        Toast.makeText(
                            this@WayangDetailActivity,
                            "Sorry Data Cannot Loaded",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<WayangResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(
                    this@WayangDetailActivity,
                    "Connection Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun setData(name : String,  description:String, origin: String, source : String, url: String){
        Glide.with(this)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .fitCenter()
            .into(binding.ivDetail)
        binding.tvJudulDetail.text = name
        binding.tvDeskripsiDetail.text = description
        binding.tvOtherInfoDesc1.text = "Asal : $origin"
        binding.tvOtherInfoDesc2.text = "Sumber : $source"
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}