package com.hawy.www.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hawy.www.R
import com.hawy.www.databinding.FragmentHomeBinding
import com.hawy.www.ui.main.session.SessionListAdapter
import com.hawy.www.ui.main.session.SessionViewModel
import com.hawy.www.ui.main.session.SessionViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    private val auth = Firebase.auth
    private val db = Firebase.firestore

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
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SessionListAdapter(this.requireContext())
        binding.rvSessions.adapter = adapter

//        sessionViewModel.getUserFollowing()

        sessionViewModel.getSessionsByFollowings(sessionViewModel.userFollowing)
//
        sessionViewModel.sessionsByFollowings.observe(viewLifecycleOwner) {

//            Log.d("TAG", "onViewCreated: $it")

            it.let {
                adapter.submitList(it)
            }

        }

        binding.addSessionBtn.setOnClickListener {

            val action = HomeFragmentDirections.actionHomeFragmentToAddSessionFragment()

            findNavController().navigate(action)
        }
    }
}