package com.kma_kit.smarthome.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.FirebaseApp
import com.kma_kit.smarthome.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        Thread.sleep(3000)

        navigate()
        startActivity(intent)
    }

    fun navigate () {
        val preferencesHelper = PreferencesHelper.getInstance()
        if (preferencesHelper.authToken != null) {
            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
        }
        else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}