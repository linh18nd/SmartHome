package com.kma_kit.smarthome.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.kma_kit.smarthome.R
import com.kma_kit.smarthome.ui.activity.ChangePasswordActivity
import com.kma_kit.smarthome.ui.activity.HomeScreenActivity
import com.kma_kit.smarthome.ui.activity.LoginActivity

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_settings, container, false)
        val aboutMeButton: CardView = view.findViewById(R.id.about_me_button)
        val changeUsernameButton:CardView = view.findViewById(R.id.change_username_button)
        val changePasswordButton: CardView = view.findViewById(R.id.change_password_button)
        val logoutButton: CardView = view.findViewById(R.id.logout_button)

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
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }


        return  view
    }
}
