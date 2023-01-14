package com.hawy.www.ui.main

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hawy.www.R
import com.hawy.www.data.model.userInfo.User
import com.hawy.www.databinding.FragmentEditProfileBinding
import com.hawy.www.ui.main.userinfo.UserInfoViewModel
import com.hawy.www.ui.main.userinfo.UserInfoViewModelFactory

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    val binding get() = _binding!!

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
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userInfoViewModel.getUserInfo()

        userInfoViewModel.userInfo.observe(viewLifecycleOwner) {

//            Log.d("TAG", "onViewCreated: $it")

//            binding.userImage.setImageURI(auth.currentUser?.photoUrl)
//            Glide.with(requireContext()).load(it.photoUri).into(binding.userImage)
            binding.firstNameEditText.setText(it.firstName)
            binding.lastNameEditText.setText(it.lastName)
            binding.bioEditText.setText(it.bio)

        }

        binding.editProfileBtn.setOnClickListener {

            userInfoViewModel.editProfile(
                User(
                    firstName = binding.firstNameEditText.text.toString(),
                    lastName = binding.lastNameEditText.text.toString(),
                    bio = binding.bioEditText.text.toString(),
                    displayName = userInfoViewModel.userInfo.value?.displayName,
                    email = userInfoViewModel.userInfo.value?.email,
                    nickName = userInfoViewModel.userInfo.value?.nickName,
                    photoUri = userInfoViewModel.userInfo.value?.photoUri,
                    followingUsers = userInfoViewModel.userInfo.value?.followingUsers!!
                )
            )

            val action = EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment()

            findNavController().navigate(action)

//            val user = Firebase.auth.currentUser
//
//            val profileUpdates = userProfileChangeRequest {
//                displayName = "Jane Q. User"
////                photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
//            }
//
//            user!!.updateProfile(profileUpdates)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Log.d("TAG", "User profile updated.")
//                    }
//                }

        }
    }
}