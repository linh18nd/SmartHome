package com.kma_kit.smarthome.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kma_kit.smarthome.R
import com.kma_kit.smarthome.data.entity.DeviceTabbar
import com.kma_kit.smarthome.ui.adapter.DeviceAdapter

class HomeScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val devices = listOf(
            DeviceTabbar("Light", "Phillips hue", true),
            DeviceTabbar("Air Conditioner", "LG S3", false),
            DeviceTabbar("Smart TV", "LG A1", false),
            DeviceTabbar("Router", "D-link 422", true)
        )
        val adapter = DeviceAdapter(devices)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
