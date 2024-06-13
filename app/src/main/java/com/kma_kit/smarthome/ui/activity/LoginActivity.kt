package com.kma_kit.smarthome.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.kma_kit.smarthome.R
import com.kma_kit.smarthome.databinding.ActivityLoginBinding

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

        btnLogin.setOnClickListener {
            val username = edtUserName.text.toString()
            val password = edtPassword.text.toString()

            if (username == "admin" && password == "admin") {
                // Đăng nhập thành công, chuyển sang HomeScreenActivity
                val intent = Intent(this, HomeScreenActivity::class.java)
                startActivity(intent)

            } else {
                // Đăng nhập thất bại, hiển thị thông báo lỗi
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}