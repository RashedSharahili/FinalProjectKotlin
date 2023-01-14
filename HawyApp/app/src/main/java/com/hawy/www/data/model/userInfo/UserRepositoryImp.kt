package com.hawy.www.data.model.userInfo

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hawy.www.data.model.session.Session
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class UserRepositoryImp : UserData {

    private var auth = Firebase.auth
    private val db = Firebase.firestore

    override suspend fun getUserInfo(): Flow<User> = callbackFlow {

        db.collection("Users")
            .document(auth.currentUser?.uid!!)
            .get()
            .addOnSuccessListener {

                val userInfo = it.toObject(User::class.java)

                if (userInfo != null) {

                    trySend(userInfo)

                } else if (userInfo == null) {

                    Log.e("TAG", "why null:${userInfo}")

                }
            }.addOnCompleteListener {
                Log.d("TAG", "Complete")
            }
            .addOnFailureListener {
                Log.e("TAG", "Filed")
            }
        awaitClose {}
    }

    override suspend fun getUserFollowing(): Flow<List<String>> = callbackFlow {

        db.collection("Users")
            .document(auth.currentUser?.uid!!)
            .get()
            .addOnSuccessListener {


            }
    }

    override suspend fun editProfile(user: User) {

        db.collection("Users")
            .document(auth.currentUser?.uid!!)
            .set(user)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot written with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error editing document", e)
            }
    }

    override suspend fun getUserSession(): Flow<List<Session>> = callbackFlow {

        db.collection("Sessions")
            .whereEqualTo("userId", auth.currentUser?.uid!!)
            .addSnapshotListener { snap, e ->

                if(e != null) {

                    return@addSnapshotListener
                }

                val userSessions = mutableListOf<Session>()

                snap?.documents?.forEach {

                    val userSession = it.toObject(Session::class.java)
                    userSessions.add(userSession!!)
                    Log.d("TAG", "getUserSession: $userSessions")
                }

                trySend(userSessions)
            }
        awaitClose {

        }
    }

}