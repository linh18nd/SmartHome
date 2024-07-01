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

class DeviceAdapter(
    private val devices: List<Device>,
    private val onSwitchClickListener: (Device, Boolean) -> Unit
) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

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
        val device = devices[position]
        holder.deviceName.text = device.name
        holder.deviceType.text = device.deviceType
        holder.deviceSwitch.isChecked = device.isAuto

        holder.switchListener = { isChecked ->
            // Cập nhật trạng thái của thiết bị
            device.isAuto = isChecked
            // Gọi callback để thông báo về bên ngoài
            onSwitchClickListener(device, isChecked)
        }
    }

    override fun getItemCount(): Int {
        return devices.size
    }
}
