package com.hawy.www.ui.main

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hawy.www.R
import com.hawy.www.data.model.session.Session
import com.hawy.www.data.model.session.documentId
import com.hawy.www.databinding.FragmentAddSessionBinding
import com.hawy.www.ui.auth.currentLocation
import com.hawy.www.ui.main.session.SessionViewModel
import com.hawy.www.ui.main.session.SessionViewModelFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AddSessionFragment : Fragment() {

    private var _binding: FragmentAddSessionBinding? = null
    val binding get() = _binding!!

    private val auth = Firebase.auth
    private val db = Firebase.firestore

    private var categorySelected = ""

    private val sessionViewModel: SessionViewModel by activityViewModels {
        SessionViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentAddSessionBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categories = resources.getStringArray(R.array.categories)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, categories)
        binding.categoriesMenuList.setAdapter(arrayAdapter)

        binding.categoriesMenuList.setOnItemClickListener { adapterView, view, i, l ->

            categorySelected= adapterView.getItemAtPosition(i).toString()
//            Toast.makeText(requireContext(), "$item", Toast.LENGTH_SHORT).show()
        }

        binding.addSessionBtn.setOnClickListener {

            val now = LocalDateTime.now()
            val formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss")

            sessionViewModel.addSession(
                Session(
                    category = categorySelected,
                    date = formatter.format(now),
                    details = binding.detailsEditText.text.toString(),
                    isClosed = false,
                    lat = currentLocation?.latitude,
                    long = currentLocation?.longitude,
                    phoneNumber = binding.phoneEditText.text.toString(),
                    sesstionID = documentId,
                    timeInterval = 0.0,
                    title = binding.titleEditText.text.toString(),
                    userID = auth.currentUser?.uid
                )
            )

            Toast.makeText(requireContext(), "Session has been Added", Toast.LENGTH_SHORT).show()

            val action = AddSessionFragmentDirections.actionAddSessionFragmentToHomeFragment()

            findNavController().navigate(action)
        }
    }

}