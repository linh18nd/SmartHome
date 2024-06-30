// UserInfoFragment.kt
package com.kma_kit.smarthome.ui.fragment

import RootController
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kma_kit.smarthome.R
import java.io.IOException


class UserInfoFragment : Fragment() {

    private lateinit var avatarImageView: ImageView
    private lateinit var usernameTextView: TextView
    private lateinit var firstNameTextView: TextView
    private lateinit var lastNameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var birthDateTextView: TextView
    private lateinit var genderTextView: TextView
    private val rootController: RootController by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_users, container, false)

        avatarImageView = view.findViewById(R.id.avatar)
        usernameTextView = view.findViewById(R.id.username)
        firstNameTextView = view.findViewById(R.id.first_name)
        lastNameTextView = view.findViewById(R.id.last_name)
        emailTextView = view.findViewById(R.id.email)
        birthDateTextView = view.findViewById(R.id.date_of_birth)
        genderTextView = view.findViewById(R.id.gender)

        // Set user information (for demonstration purposes, replace with actual data)
        try {
            val inputStream = requireContext().assets.open("img_avatar.jpeg")
            val bitmap = BitmapFactory.decodeStream(inputStream)
            avatarImageView.setImageBitmap(bitmap)

        } catch (e: IOException) {
            e.printStackTrace()
        }

        rootController.userInfo.observe(viewLifecycleOwner) {
            usernameTextView.text = it.first_name + " " + it.last_name
            firstNameTextView.text = it.first_name
            birthDateTextView.text = it.date_of_birth
            if (it.gender) {
                genderTextView.text = "Male"
            } else genderTextView.text = "Female"
            lastNameTextView.text = it.last_name
            emailTextView.text = it.email

        }
        return view
    }

    private fun getDataMe() {

    }
}
