package com.example.dalangapp.loginregis

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dalangapp.databinding.ActivityRegisterBinding
import com.example.dalangapp.retrofit.ApiConfig
import com.example.dalangapp.retrofit.responses.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setMyButtonEnable()


        binding.idMyNamaEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable) {

            }
        })


        binding.idMyEmailEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable) {

            }
        })


        binding.idMyPassEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        binding.idMyConfirmPassEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        binding.idMyButton.setOnClickListener {
            val thisName = binding.idMyNamaEt.text.toString()
            val thisEmail = binding.idMyEmailEt.text.toString()
            val thisPassword = binding.idMyPassEt.text.toString()
            val thisConfPassword = binding.idMyConfirmPassEt.text.toString()
            startRegister(thisName, thisEmail, thisPassword, thisConfPassword)
        }

        binding.idToSignInTv.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }
    }

    private fun startRegister(name: String, email: String, password: String, confPassword: String) {
        showLoading(true)
        val client = ApiConfig.getApiService(this).register(
            name, email, password, confPassword
        )
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    showLoading(false)
                    Toast.makeText(this@RegisterActivity, responseBody?.msg, Toast.LENGTH_SHORT)
                        .show()
                    Intent(this@RegisterActivity, LoginActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                } else {
                    showLoading(false)
                    Toast.makeText(this@RegisterActivity, response.message(), Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@RegisterActivity, "Gagal instance Retrofit", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun setMyButtonEnable() {
        val enteredName = binding.idMyNamaEt.text
        val enteredEmail = binding.idMyEmailEt.text
        val enteredPass = binding.idMyPassEt.text
        val enteredConfPass = binding.idMyConfirmPassEt.text


        binding.idMyButton.isEnabled =
            enteredName != null && enteredName.toString()
                .isNotEmpty() && enteredEmail != null && enteredEmail.toString()
                .isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(enteredEmail.toString())
                .matches() && enteredPass != null && enteredPass.toString()
                .isNotEmpty() && enteredPass.length >= 6 && enteredConfPass != null && enteredConfPass.toString()
                .isNotEmpty() && enteredConfPass.length >= 6 && enteredConfPass.toString()
                .equals(enteredPass.toString())
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}