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
import com.hawy.www.databinding.FragmentRegisterBinding
import com.hawy.www.ui.main.MainActivity

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()

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

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.haveUserClick.setOnClickListener {

            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()

            findNavController().navigate(action)

        }

        binding.registerBtn.setOnClickListener {

            registerUser()
        }
    }

    fun registerUser() {

        when {

            TextUtils.isEmpty(binding.usernameEditText.text.toString().trim { it <= ' ' }) -> {

                Toast.makeText(requireContext(), "Please Enter Username", Toast.LENGTH_SHORT).show()
            }

            TextUtils.isEmpty(binding.emailEditText.text.toString().trim { it <= ' ' }) -> {

                Toast.makeText(requireContext(), "Please Enter Email", Toast.LENGTH_SHORT).show()
            }

            TextUtils.isEmpty(binding.passwordEditText.text.toString().trim { it <= ' ' }) -> {

                Toast.makeText(requireContext(), "Please Enter Paswword", Toast.LENGTH_SHORT).show()
            }

            TextUtils.isEmpty(binding.rePasswordEditText.text.toString().trim { it <= ' ' }) -> {

                Toast.makeText(requireContext(), "Please Enter re-Passwprd", Toast.LENGTH_SHORT).show()
            }

            binding.passwordEditText.text.toString().trim { it <= ' ' } != binding.rePasswordEditText.text.toString().trim { it <= ' ' } -> {

                Toast.makeText(requireContext(), "Password Not Matches", Toast.LENGTH_SHORT).show()
            }

            else -> {

                val user_name: String = binding.usernameEditText.text.toString().trim { it <= ' ' }
                val email: String = binding.emailEditText.text.toString().trim { it <= ' ' }
                val password: String = binding.passwordEditText.text.toString().trim { it <= ' ' }
                authViewModel.registerUser(user_name, email, password)

                authViewModel.isCreated.observe(viewLifecycleOwner) { isCreated ->

                    if(isCreated) {

                        Toast.makeText(requireContext(), "User created successfully", Toast.LENGTH_SHORT).show()

                        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()

                        findNavController().navigate(action)

                    } else {

                        authViewModel.errorException.observe(viewLifecycleOwner) {

                            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                        }

//                        Toast.makeText(requireContext(), authViewModel.errorException.value, Toast.LENGTH_SHORT).show()
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