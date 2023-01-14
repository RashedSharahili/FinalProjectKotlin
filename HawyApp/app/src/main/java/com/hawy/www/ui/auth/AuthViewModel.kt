package com.hawy.www.ui.auth

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hawy.www.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@SuppressLint("StaticFieldLeak")
class AuthViewModel() : ViewModel() {

    private var _auth = Firebase.auth
    val auth get() = _auth

    private var _isSuccess = MutableLiveData<Boolean>()
    val isSuccess : LiveData<Boolean> = _isSuccess

    private var _isCreated = MutableLiveData<Boolean>()
    val isCreated : LiveData<Boolean> = _isCreated

    private val db = Firebase.firestore

    var errorException = MutableLiveData<String>()

    fun loginUser(email: String, password: String) {

        Log.d("TAG", "loginUser: $_isSuccess")

        // Create an instance and create a register a user with email and password
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                // If the authentication is successfully done
                if(task.isSuccessful) {

                    _isSuccess.value = true

                    Log.d("TAG", "loginUser: ${task.isSuccessful}")

                    val user = Firebase.auth.currentUser

                    updateUI(user)

                } else {

                    _isSuccess.value = false

                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {

        user?.let { user ->
            // Name, email address, and profile photo Url
            val user_name = user.displayName
            val email = user.email
//            val photoUrl = user.photoUrl
            val photoUrl = user.photoUrl
            val phoneNumber = user.phoneNumber

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid

            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(user_name)
                .setPhotoUri(photoUrl)
                .build()
        }

//        Log.d("TAG", "updateUI uid: ${user?.displayName}")
    }

    fun registerUser(user_name: String, email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    _isCreated.value = true

                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    val user = auth.currentUser
                    initUpdateUI(user, user_name)

                } else {

                    _isCreated.value = false

                    errorException.value = task.exception?.message.toString()
//                    Log.d("Exception", "registerUser: $errorException")
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    initUpdateUI(null, "")
                }
            }

    }

    private fun initUpdateUI(user: FirebaseUser?, user_name: String) {

        user?.let { user ->

            // Name, email address, and profile photo Url
//            val user_name = user.displayName
            val username = user_name
            val email = user.email
            val nickname = user_name
//            val photoUrl = user.photoUrl
//            val photoUrl = Uri.parse("android.resource://com.hawy.www/${R.drawable.default_profile}")
            val photoUrl = Uri.parse("https://firebasestorage.googleapis.com/v0/b/finalproject-3363c.appspot.com/o/default_profile.png?alt=media&token=07f9fa91-a418-42ca-880f-eb9ecf802b85")

            val profileInfo = UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .setPhotoUri(photoUrl)
                .build()

            val c_profileInfo = hashMapOf(

                "displayName" to username,
                "email" to email,
                "photoUri" to photoUrl,
                "nickName" to nickname,
                "followingUsers" to listOf<String>(),
                "firstName" to "",
                "lastName" to "",
                "bio" to ""
            )

            CoroutineScope(Dispatchers.IO).launch {

                try {

                    user.updateProfile(profileInfo).await()
                    withContext(Dispatchers.Main) {

                        Log.d("TAG", "initUpdateUI: Success")

                        db.collection("Users").document(user.uid)
                            .set(c_profileInfo)
                            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
                            .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
                    }

                } catch (e: Exception) {

                    withContext(Dispatchers.Main) {

                        Log.d("TAG", "initUpdateUI: ${e.message}")
                    }
                }
            }

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid

        }

    }

    fun logoutUser() {

        auth.signOut()
    }
}