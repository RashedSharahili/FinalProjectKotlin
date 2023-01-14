package com.hawy.www.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hawy.www.ui.main.MainActivity
import com.hawy.www.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload()
        }
    }

    private fun reload() {

        val intent = Intent(requireContext(), MainActivity::class.java)

        startActivity(intent)
        activity?.finish()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerBtn.setOnClickListener {

            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()

            findNavController().navigate(action)
        }

        binding.loginBtn.setOnClickListener {

            loginUser()
        }

        binding.forgetClick.setOnClickListener {

            val action = LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment()

            findNavController().navigate(action)
        }
    }

    fun loginUser() {

        when {

            TextUtils.isEmpty(binding.emailEditText.text.toString().trim { it <= ' ' }) -> {

                Toast.makeText(requireContext(), "Please Enter Email", Toast.LENGTH_SHORT).show()
            }

            TextUtils.isEmpty(binding.passwordEditText.text.toString().trim { it <= ' ' }) -> {

                Toast.makeText(requireContext(), "Please Enter Paswword", Toast.LENGTH_SHORT).show()
            }

            else -> {

                val email: String = binding.emailEditText.text.toString().trim { it <= ' ' }
                val password: String = binding.passwordEditText.text.toString().trim { it <= ' ' }
                authViewModel.loginUser(email, password)

//                authViewModel.isSuccess.value.let {
//
//                    when(it) {
//                        true -> {
//
//                            Toast.makeText(requireContext(), "You were login successfully", Toast.LENGTH_SHORT).show()
//
//                            val intent = Intent(requireContext(), MainActivity::class.java)
//
//                            startActivity(intent)
//                            activity?.finish()
//                        }
//                        else -> {
//
//                            Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }

                authViewModel.isSuccess.observe(viewLifecycleOwner){

                    if(it) {

                        Toast.makeText(requireContext(), "You were login successfully", Toast.LENGTH_SHORT).show()

                        val intent = Intent(requireContext(), MainActivity::class.java)

                        startActivity(intent)
                        activity?.finish()

                    } else {

                        Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show()

                    }

                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}