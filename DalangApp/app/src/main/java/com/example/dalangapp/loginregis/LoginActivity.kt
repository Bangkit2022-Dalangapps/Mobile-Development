package com.example.dalangapp.loginregis

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dalangapp.MainActivity
import com.example.dalangapp.R
import com.example.dalangapp.customui.MyButton
import com.example.dalangapp.customui.MyEditText
import com.example.dalangapp.customui.MyPassEditText
import com.example.dalangapp.retrofit.ApiConfig
import com.example.dalangapp.retrofit.SessionManager
import com.example.dalangapp.retrofit.responses.DataItemLogin
import com.example.dalangapp.retrofit.responses.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var myEnterButton: MyButton
    private lateinit var myEmailEditText: MyEditText
    private lateinit var myPassEditText: MyPassEditText
    private lateinit var edToRegister: TextView
    private lateinit var progressBar: ProgressBar

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sessionManager: SessionManager

    private var SHARED_PREF_NAME = "mypref"
    private var KEY_ID = "key_id"
    private var KEY_NAME = "key_name"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        myEnterButton = findViewById(R.id.id_my_button)
        myEmailEditText = findViewById(R.id.id_my_email_et)
        myPassEditText = findViewById(R.id.id_my_pass_et)
        edToRegister = findViewById(R.id.id_register_tv)
        progressBar = findViewById(R.id.progress_bar)

        setMyButtonEnable()

        sessionManager = SessionManager(this)


        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
        val id = sharedPreferences.getString(KEY_ID, null)
        if (id != null) {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }



        myEmailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        myPassEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        myEnterButton.setOnClickListener {
            val thisEmail = myEmailEditText.text.toString()
            val thisPassword = myPassEditText.text.toString()
            startLogin(thisEmail, thisPassword)
        }

        edToRegister.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }


    }

    private fun startLogin(name: String, password: String) {
        showLoading(true)
        val client = ApiConfig.getApiService(this).login(
            name,
            password
        )
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    showLoading(false)
                    Toast.makeText(this@LoginActivity, responseBody?.message, Toast.LENGTH_SHORT)
                        .show()
                    if (responseBody != null) {
                        setSession(responseBody.data)
                        sessionManager.saveAuthToken(responseBody.token)
                        Intent(this@LoginActivity, MainActivity::class.java).also {
                            startActivity(it)
                            finish()
                        }
                    }
                } else {
                    showLoading(false)
                    Toast.makeText(this@LoginActivity, response.message(), Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@LoginActivity, "Gagal instance Retrofit", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun setSession(session: List<DataItemLogin>) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_ID, session[0].id.toString())
        editor.putString(KEY_NAME, session[0].name)
        editor.apply()
    }

    private fun setMyButtonEnable() {
        val enteredEmail = myEmailEditText.text
        val enteredPass = myPassEditText.text
        myEnterButton.isEnabled = enteredEmail != null && enteredEmail.toString()
            .isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(enteredEmail.toString())
            .matches() && enteredPass != null && enteredPass.toString()
            .isNotEmpty() && enteredPass.length >= 6
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

}