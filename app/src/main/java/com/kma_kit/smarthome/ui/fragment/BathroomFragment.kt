package com.kma_kit.smarthome.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kma_kit.smarthome.R
import com.kma_kit.smarthome.data.entity.DeviceTabbar
import com.kma_kit.smarthome.ui.adapter.DeviceAdapter

class BathroomFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bathroom, container, false)

        // Khởi tạo danh sách thiết bị mẫu
        val devices = listOf(
            DeviceTabbar("Water Heater", "Bosch Tronic 3000", false)
        )

        // Khởi tạo và thiết lập Adapter cho RecyclerView
        val deviceAdapter = DeviceAdapter(devices)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewBathroom)
        recyclerView.adapter = deviceAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view
    }
}
