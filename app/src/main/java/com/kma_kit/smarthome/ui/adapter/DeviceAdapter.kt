package com.kma_kit.smarthome.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kma_kit.smarthome.R
import com.kma_kit.smarthome.data.model.response.Device

class DeviceAdapter(private val devices: List<Device>) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    inner class DeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deviceName: TextView = itemView.findViewById(R.id.deviceName)
        val deviceType: TextView = itemView.findViewById(R.id.deviceType)
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        val deviceSwitch: Switch = itemView.findViewById(R.id.deviceSwitch)

        var switchListener: ((Boolean) -> Unit)? = null

        init {
            deviceSwitch.setOnCheckedChangeListener { _, isChecked ->
                switchListener?.invoke(isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false)
        return DeviceViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        var device = devices[position]
        holder.deviceName.text = device.name
        holder.deviceType.text = device.deviceType
        holder.deviceSwitch.isChecked = device.isAuto

        holder.switchListener = { isChecked ->
            // Xử lý sự kiện khi Switch thay đổi trạng thái (isChecked là trạng thái mới của Switch)
            device.isAuto = isChecked
            // Gọi hàm hoặc thông báo về bên ngoài (nếu cần)
        }
    }

    override fun getItemCount(): Int {
        return devices.size
    }
}
