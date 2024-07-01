package com.kma_kit.smarthome.ui.fragment

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
import com.kma_kit.smarthome.data.model.response.HomeResponse
import com.kma_kit.smarthome.services.api.ApiService
import com.kma_kit.smarthome.ui.adapter.DeviceAdapter
import kotlinx.coroutines.launch
import retrofit2.Response

class AllFragment : Fragment() {

    private lateinit var deviceAdapter: DeviceAdapter
    private val listDevices = mutableListOf<Device>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewAll)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Khởi tạo Adapter
        deviceAdapter = DeviceAdapter(listDevices)
        recyclerView.adapter = deviceAdapter

        // Gọi hàm fetchAndDisplayDevices để lấy dữ liệu từ API
        fetchAndDisplayDevices()

        return view
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
                            listDevices.addAll(room.devices)
                        }
                        deviceAdapter.notifyDataSetChanged() // Cập nhật RecyclerView khi có dữ liệu mới
                    }
                } else {
                    println("API call failed: ${response.code()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
