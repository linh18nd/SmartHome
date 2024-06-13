package com.kma_kit.smarthome.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kma_kit.smarthome.R
import com.kma_kit.smarthome.data.model.request.UserAuth
import com.kma_kit.smarthome.repository.UserRepository
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    lateinit var edtUserName: EditText
    lateinit var edtPassword: EditText
    lateinit var btnLogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        edtUserName = findViewById(R.id.username)
        edtPassword = findViewById(R.id.password)
        btnLogin = findViewById(R.id.loginButton)
        configureListeners()
    }

    fun configureListeners() {
        btnLogin.setOnClickListener {
            Log.d("LoginActivity", "Login button clicked")
            lifecycleScope.launch {
                login()
            }
        }
    }

    suspend fun login() {
        val username = edtUserName.text.toString()
        val password = edtPassword.text.toString()
        val userAuth = UserAuth(username, password, "admin")
        val authResponse = UserRepository().loginUser(userAuth)
        if (authResponse.fcm_token != null) {
            // Login success
//            Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
        } else {
            // Login failed
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
        }


    }
}