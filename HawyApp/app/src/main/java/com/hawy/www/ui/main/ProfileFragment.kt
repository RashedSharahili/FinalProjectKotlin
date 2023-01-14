package com.hawy.www.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hawy.www.R
import com.hawy.www.databinding.FragmentLoginBinding
import com.hawy.www.databinding.FragmentProfileBinding
import com.hawy.www.ui.auth.AuthActivity
import com.hawy.www.ui.main.session.SessionListAdapter
import com.hawy.www.ui.main.userinfo.UserInfoViewModel
import com.hawy.www.ui.main.userinfo.UserInfoViewModelFactory

class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val auth = Firebase.auth
    private val db = Firebase.firestore

    private val userInfoViewModel: UserInfoViewModel by activityViewModels {

        UserInfoViewModelFactory()
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

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val adapter = SessionListAdapter(this.requireContext())
//        binding.rvUserSessions.adapter = adapter

        userInfoViewModel.getUserInfo()

        userInfoViewModel.userInfo.observe(viewLifecycleOwner) {

//            Log.d("TAG", "onViewCreated: $it")

//            binding.userImage.setImageURI(auth.currentUser?.photoUrl)
            Glide.with(requireContext()).load(it.photoUri).into(binding.userImage)
            binding.nickNameLabel.text = it.nickName
            binding.userName.text = it.displayName
            binding.bio.text = it.bio
            binding.userFollowingCountLabel.text = it.followingUsers.size.toString()

        }

        binding.editProfileBtn.setOnClickListener {

            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
            findNavController().navigate(action)
        }

//        userInfoViewModel.getUserSession()
//
//        Log.d("TAG", "onViewCreated: dfgdfhv")
//
//        userInfoViewModel.userSession.observe(viewLifecycleOwner) {
//
//            it.let {
//                adapter.submitList(it)
//            }
//        }

        binding.logoutBtn.setOnClickListener {

            logoutUser()
        }
    }

    private fun logoutUser() {

        userInfoViewModel.logoutUser()

        Toast.makeText(requireContext(), "You were logout successfully", Toast.LENGTH_SHORT).show()

        val intent = Intent(requireContext(), AuthActivity::class.java)

        startActivity(intent)
        activity?.finish()
    }
}