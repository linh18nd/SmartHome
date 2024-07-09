package com.kma_kit.smarthome.ui.fragment

import RootController
import UpdateDeviceRequest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kma_kit.smarthome.R
import com.kma_kit.smarthome.data.model.response.Device
import com.kma_kit.smarthome.data.model.response.HomeResponse
import com.kma_kit.smarthome.ui.adapter.DeviceAdapter

import kotlinx.coroutines.launch
import retrofit2.Response

class BedroomFragment : Fragment() {
    private lateinit var deviceAdapter: DeviceAdapter
    private val listDevices = mutableListOf<Device>()
    private val rootController: RootController by activityViewModels()

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.getStringExtra("message")?.let { message ->
                Log.d("BedroomFragment", "Broadcast received: $message")
                if (isAdded) {
                    fetchAndDisplayDevices()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bedroom, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewBedroom)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Khởi tạo Adapter với listener
        deviceAdapter = DeviceAdapter(listDevices,
            onIsAutoSwitchClickListener = { device, isChecked ->
                onDeviceSwitchChanged(device, isChecked)
            },
            onValueSwitchClickListener = { device, isChecked ->
                onDeviceValueSwitchChanged(device, isChecked)
            }
        )
        recyclerView.adapter = deviceAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isAdded) {
            LocalBroadcastManager.getInstance(requireContext())
                .registerReceiver(broadcastReceiver, IntentFilter("MyDataUpdate"))
            fetchAndDisplayDevices()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isAdded) {
            LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(broadcastReceiver)
        }
    }

    private fun fetchAndDisplayDevices() {
        lifecycleScope.launch {
            try {
                val response: Response<HomeResponse> = ApiClient.api.getDevices()

                if (response.isSuccessful) {
                    val homeResponse = response.body()
                    homeResponse?.let { home ->
                        listDevices.clear() // Xóa dữ liệu cũ trước khi thêm dữ liệu mới
                        home.rooms.forEach { room ->
                            if (room.id == "1d653e28-d7bf-476f-9a35-c51a1c844b73") {
                                listDevices.addAll(room.devices)
                            }
                        }
                        deviceAdapter.notifyDataSetChanged() // Cập nhật RecyclerView khi có dữ liệu mới
                    }
                } else {
                    Log.e("BedroomFragment", "API call failed: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("BedroomFragment", "Error fetching devices", e)
            }
        }
    }

    private fun onDeviceSwitchChanged(device: Device, isChecked: Boolean) {
        // Xử lý sự kiện switch isAuto
        Log.d("BedroomFragment", "Switch clicked for device ${device.name}, isChecked: $isChecked")
        lifecycleScope.launch {
            try {
                val response = ApiClient.api.updateDeviceState(
                    device.id,
                    UpdateDeviceRequest(isChecked, device.value)
                )
                if (response.isSuccessful) {
                    Log.d("BedroomFragment", "Device state updated successfully")
                } else {
                    Log.e("BedroomFragment", "Failed to update device state: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("BedroomFragment", "Error updating device state", e)
            }
        }
    }

    private fun onDeviceValueSwitchChanged(device: Device, isChecked: Boolean) {
        // Xử lý sự kiện switch value
        Log.d("BedroomFragment", "Value Switch clicked for device ${device.name}, isChecked: $isChecked")
        lifecycleScope.launch {
            try {
                val newValue = if (isChecked) 1.0 else 0.0
                val response = ApiClient.api.updateDeviceState(
                    device.id,
                    UpdateDeviceRequest(device.is_auto, newValue)
                )
                if (response.isSuccessful) {
                    Log.d("BedroomFragment", "Device value updated successfully")
                } else {
                    Log.e("BedroomFragment", "Failed to update device value: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("BedroomFragment", "Error updating device value", e)
            }
        }
    }
}
