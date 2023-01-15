package com.hawy.www.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hawy.www.R
import com.hawy.www.databinding.FragmentSessionDetailsBinding

class SessionDetailsFragment : Fragment() {

    private var _binding: FragmentSessionDetailsBinding? = null
    val binding get() = _binding!!

    private lateinit var sessionCategory: String
    private lateinit var sessionTitle: String
    private lateinit var sessionDetails: String
    private lateinit var sessionLat: String
    private lateinit var sessionLong: String
    private lateinit var userPhoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            sessionCategory = it.getString("sessionCategory").toString()
            sessionTitle = it.getString("sessionTitle").toString()
            sessionDetails = it.getString("sessionDetails").toString()
            sessionLat = it.getString("sessionLat").toString()
            sessionLong = it.getString("sessionLong").toString()
            userPhoneNumber = it.getString("userPhoneNumber").toString()

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentSessionDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sessionCategoryLabel.text = sessionCategory
        binding.sessionTitleLabel.text = sessionTitle
        binding.sessionDetailsLabel.text = sessionDetails
    }
}