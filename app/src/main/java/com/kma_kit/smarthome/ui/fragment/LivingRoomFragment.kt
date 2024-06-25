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

class LivingRoomFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_living_room, container, false)

        // Khởi tạo danh sách thiết bị mẫu
        val devices = listOf(
            DeviceTabbar("Smart TV", "LG A1", false),
            DeviceTabbar("Light", "Phillips hue", true)
        )

        // Khởi tạo và thiết lập Adapter cho RecyclerView
        val deviceAdapter = DeviceAdapter(devices)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewLivingRoom)
        recyclerView.adapter = deviceAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view
    }
}
