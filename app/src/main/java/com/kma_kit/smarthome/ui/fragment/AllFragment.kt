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

class AllFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all, container, false)

        // Khởi tạo danh sách thiết bị mẫu
        val devices = listOf(
            DeviceTabbar("Light", "Phillips hue", true),
            DeviceTabbar("Air Conditioner", "LG S3", false),
            DeviceTabbar("Smart TV", "LG A1", false),
            DeviceTabbar("Router", "D-link 422", true)
        )

        // Khởi tạo và thiết lập Adapter cho RecyclerView
        val deviceAdapter = DeviceAdapter(devices)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewAll)
        recyclerView.adapter = deviceAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view
    }
}
