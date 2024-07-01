package com.kma_kit.smarthome.ui.fragment

import UpdateDeviceRequest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kma_kit.smarthome.R
import com.kma_kit.smarthome.data.model.response.Device
import com.kma_kit.smarthome.ui.adapter.DeviceAdapter
import kotlinx.coroutines.launch

class KitchenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_kitchen, container, false)

        // Khởi tạo danh sách thiết bị mẫu
        val devices = listOf(
            Device("Water Heater", "Bosch Tronic 3000", false,"hehe",1.0)
        )

        // Khởi tạo và thiết lập Adapter cho RecyclerView
        val  deviceAdapter = DeviceAdapter(devices)
        { device, isChecked ->
            // Xử lý sự kiện khi Switch được bật/tắt
            onDeviceSwitchChanged(device, isChecked)
        }
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewKitchen)
        recyclerView.adapter = deviceAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view
    }
    private fun onDeviceSwitchChanged(device: Device, isChecked: Boolean) {
        // Xử lý sự kiện switch click tại đây
        println("Switch clicked for device ${device.name}, isChecked: $isChecked")

        // Gọi API để cập nhật trạng thái thiết bị trên server
        lifecycleScope.launch {
            try {
                val response = ApiClient.api.updateDeviceState(device.id, UpdateDeviceRequest(isChecked,0))
                if (response.isSuccessful) {
                    println("Device state updated successfully")
                } else {
                    println("Failed to update device state: ${response.code()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}