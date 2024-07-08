package com.kma_kit.smarthome.ui.fragment

import RootController
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.kma_kit.smarthome.R
import com.kma_kit.smarthome.ui.activity.NotificationsActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {
    private lateinit var notificationIcon: ImageView
    private val rootController: RootController by activityViewModels()
    private lateinit var temperatureTextView: TextView
    private lateinit var gasTextView: TextView
    private lateinit var humidityTextView: TextView
    private lateinit var lightTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        temperatureTextView = view.findViewById(R.id.temperatureTextView)
        gasTextView = view.findViewById(R.id.gasTextView)
        humidityTextView = view.findViewById(R.id.humidityTextView)
        lightTextView = view.findViewById(R.id.lightTextView)
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)
        val currentDate = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date())
        dateTextView.text = currentDate

        rootController.devices.observe(viewLifecycleOwner, Observer { devices ->
            Log.d("HomeFragment", "LiveData updated: $devices")
            devices.forEach { deviceEntity ->
                when (deviceEntity.type) {
                    "humidity" -> humidityTextView.text = deviceEntity.value.toString() + "%"
                    "temperature" -> temperatureTextView.text = deviceEntity.value.toString() + "°C"
                    "gas" -> gasTextView.text = deviceEntity.value.toString()+ "%"
                    "bulb" -> lightTextView.text = deviceEntity.value.toString()
                }
            }
        })

        val tabLayout: TabLayout = view.findViewById(R.id.tabLayout)
        notificationIcon = view.findViewById(R.id.notificationIcon)

        listenEvent()

        // Set initial fragment
        if (savedInstanceState == null) {
            childFragmentManager.commit {
                replace(R.id.fragmentContainer, AllFragment())
            }
        }

        // Set up tab selected listener
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val selectedFragment = when (tab.position) {
                    0 -> AllFragment()
                    1 -> LivingRoomFragment()
                    2 -> KitchenFragment()
                    3 -> BedroomFragment()
                    else -> AllFragment()
                }
                childFragmentManager.commit {
                    replace(R.id.fragmentContainer, selectedFragment)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // Do nothing
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // Do nothing
            }
        })

        return view
    }

    fun listenEvent () {
        notificationIcon.setOnClickListener {
            var intent = Intent(context, NotificationsActivity::class.java)
            startActivity(intent)
        }
    }
}
