package com.kma_kit.smarthome.ui.fragment

import RootController
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.kma_kit.smarthome.R
import com.kma_kit.smarthome.data.model.response.UserResponse
import com.kma_kit.smarthome.repository.UserRepository
import com.kma_kit.smarthome.ui.activity.ChangePasswordActivity
import com.kma_kit.smarthome.ui.activity.HomeScreenActivity
import com.kma_kit.smarthome.ui.activity.LoginActivity
import kotlinx.coroutines.launch
import okio.IOException

class SettingsFragment : Fragment() {
    private lateinit var aboutMeButton: CardView
    private lateinit var changeUsernameButton: CardView
    private lateinit var changePasswordButton: CardView
    private lateinit var logoutButton: CardView
    private lateinit var avatar: ImageView
    private lateinit var userName : TextView
    private lateinit var enableNoti : Switch
    private lateinit var enableDarkMode : Switch
    private val rootController: RootController by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_settings, container, false)
        onInit(view)
        listenerEvent()
        return view
    }

    private fun onInit(view: View) {
        aboutMeButton = view.findViewById(R.id.about_me_button)
        changeUsernameButton = view.findViewById(R.id.change_username_button)
        changePasswordButton = view.findViewById(R.id.change_password_button)
        logoutButton = view.findViewById(R.id.logout_button)
        avatar = view.findViewById(R.id.avatar)
        userName = view.findViewById(R.id.username)
        enableNoti = view.findViewById(R.id.switch_notifications)
        enableDarkMode = view.findViewById(R.id.switch_dark_mode)
        rootController.userInfo.observe(viewLifecycleOwner) {
            userName.text = it.first_name + " " + it.last_name
            enableNoti.isEnabled = it.fcm_token.isNotEmpty()

        }
        val preferencesHelper = PreferencesHelper.getInstance()
        enableDarkMode.isChecked = preferencesHelper.enableDarkMode

        try {
            val inputStream = requireContext().assets.open("img_avatar.jpeg")
            val bitmap = BitmapFactory.decodeStream(inputStream)
            avatar.setImageBitmap(bitmap)

        } catch (e: IOException) {
            e.printStackTrace()
        }
        // Do nothing
    }



    private fun listenerEvent() {
        enableNoti.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

            } else {

            }
        }

        enableDarkMode.setOnCheckedChangeListener { _, isChecked ->
            val preferencesHelper = PreferencesHelper.getInstance()
            preferencesHelper.enableDarkMode = isChecked
        }

        aboutMeButton.setOnClickListener {
            var url: String = "https://smarthomekit.vn/"
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://$url"
            }

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }

        changePasswordButton.setOnClickListener {
            val context = requireContext()
            val intent = Intent(context, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        logoutButton.setOnClickListener {
            val context = requireContext()
            val preferencesHelper = PreferencesHelper.getInstance()
            preferencesHelper.clear()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
