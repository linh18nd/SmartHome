package com.kma_kit.smarthome.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
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
    }
}