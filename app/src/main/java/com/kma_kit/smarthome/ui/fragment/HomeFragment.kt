package com.kma_kit.smarthome.ui.fragment

import RootController
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.kma_kit.smarthome.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {
    private val rootController: RootController by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)
        val currentDate = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date())
        dateTextView.text = currentDate
        val tabLayout: TabLayout = view.findViewById(R.id.tabLayout)

        // Set initial fragment
        if (savedInstanceState == null) {
            childFragmentManager.commit {
                replace(R.id.fragmentContainer, AllFragment())
            }
        }
        rootController.devices.observe(viewLifecycleOwner, Observer { devices ->
            Log.d("AllFragment", "LiveData updated: $devices")
            devices.forEach { deviceEntity ->
                if (deviceEntity.type == "humidity") {
                    Log.d("humidity", deviceEntity.toString())
                }
            }
        })


        // Set up tab selected listener
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val selectedFragment = when (tab.position) {
                    0 -> AllFragment()
                    1 -> LivingRoomFragment()
                    2 -> KitchenFragment()
                    3 -> BathroomFragment()
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
}
