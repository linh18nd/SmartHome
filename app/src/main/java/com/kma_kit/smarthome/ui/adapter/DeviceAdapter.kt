package com.kma_kit.smarthome.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kma_kit.smarthome.R
import com.kma_kit.smarthome.data.model.response.Device

class DeviceAdapter(
    private val devices: List<Device>,
    private val onIsAutoSwitchClickListener: (Device, Boolean) -> Unit,
    private val onValueSwitchClickListener: (Device, Boolean) -> Unit
) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    inner class DeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deviceName: TextView = itemView.findViewById(R.id.deviceName)
        val deviceType: TextView = itemView.findViewById(R.id.deviceType)
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        val deviceSwitch: Switch = itemView.findViewById(R.id.deviceSwitch1)
        val deviceValueSwitch: Switch = itemView.findViewById(R.id.deviceSwitch2)
        val deviceImage: ImageView = itemView.findViewById(R.id.imageIcon)

        init {
            deviceSwitch.setOnCheckedChangeListener { _, isChecked ->
                onIsAutoSwitchClickListener(devices[adapterPosition], isChecked)
            }

            deviceValueSwitch.setOnCheckedChangeListener { _, isChecked ->
                onValueSwitchClickListener(devices[adapterPosition], isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false)
        return DeviceViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = devices[position]
        holder.deviceName.text = device.name
        holder.deviceType.text = device.device_type
        holder.deviceSwitch.isChecked = device.is_auto
        holder.deviceValueSwitch.isChecked = device.value == 1.0

        // Set image based on device type
        when (device.device_type) {
            "bulb" -> holder.deviceImage.setImageResource(R.drawable.light)
            "water" -> holder.deviceImage.setImageResource(R.drawable.humidity)
            "fan" -> holder.deviceImage.setImageResource(R.drawable.ic_fan)
            "humidity" -> holder.deviceImage.setImageResource(R.drawable.humidity)
            else -> holder.deviceImage.setImageResource(R.drawable.temprature)
        }

        // Thay đổi màu nền dựa trên giá trị của device.value
        // (Bạn có thể thêm logic ở đây nếu cần thiết)

        // Cài đặt listener cho switch isAuto
        holder.deviceSwitch.setOnCheckedChangeListener(null) // Remove previous listener
        holder.deviceSwitch.isChecked = device.is_auto // Set checked state
        holder.deviceSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Cập nhật trạng thái của thiết bị
            device.is_auto = isChecked
            // Gọi callback để thông báo về bên ngoài
            onIsAutoSwitchClickListener(device, isChecked)
        }

        // Cài đặt listener cho switch value
        holder.deviceValueSwitch.setOnCheckedChangeListener(null) // Remove previous listener
        holder.deviceValueSwitch.isChecked = device.value == 1.0 // Set checked state
        holder.deviceValueSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Cập nhật trạng thái của thiết bị
            device.value = if (isChecked) 1.0 else 0.0
            // Gọi callback để thông báo về bên ngoài
            onValueSwitchClickListener(device, isChecked)
        }
    }

    override fun getItemCount(): Int {
        return devices.size
    }
}
