package com.hawy.www.data.model.session

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.*

val documentId = UUID.randomUUID().toString()

class SessionRepositoryImp: SessionData {

    private var auth = Firebase.auth
    private val db = Firebase.firestore

//    private var sessionList = mutableListOf<Session>()

    override suspend fun getSessions(): Flow<List<Session>> = callbackFlow {

        db.collection("Sessions")
            .whereEqualTo("isClosed", false)
            .whereNotEqualTo("userID", auth.currentUser?.uid)
            .addSnapshotListener { snap, e ->

                if(e != null) {

                    return@addSnapshotListener
                }

                val sessions = mutableListOf<Session>()

                snap?.documents?.forEach {

                    if(it.exists()) {

                        val session = it.toObject(Session::class.java)
                        sessions.add(session!!)
//                        Log.d("TAG", "getSessions: $sessions")
                    }
                }

                trySend(sessions)
            }
        awaitClose{

        }

    }

    override suspend fun getUserFollowing(): List<String> {

        var userFollowings = listOf<String>()

        db.collection("Users")
            .document(auth.currentUser?.uid!!)
            .get()
            .addOnSuccessListener {

//                Log.d("TAG", "getUserFollowings: ${it.data?.get("followingUsers")}")

                userFollowings = it.data?.get("followingUsers") as List<String>

                Log.d("TAG", "getUserFollowings: $userFollowings")
            }

        return userFollowings
    }

    override suspend fun getSessionsByFollowings(userFollowing: List<String>): Flow<List<Session>> = callbackFlow {

        db.collection("Sessions")
            .whereIn("userID", listOf("8Qy0mordORdJotOLaWr5Yuh7MBo2", "nx7dMUSA9mY9ehjOhILCNhwny102"))
            .whereEqualTo("isClosed", false)
            .addSnapshotListener { snap, e ->

                if(e != null) {

                    return@addSnapshotListener
                }

                val sessions = mutableListOf<Session>()

                snap?.documents?.forEach {

                    val session = it.toObject(Session::class.java)
                    sessions.add(session!!)
                    Log.d("TAG", "getSessionsByFollowings: $sessions")
                }

                trySend(sessions)
            }
        awaitClose {

        }

    }

    override suspend fun addSession(session: Session) {

        db.collection("Sessions")
            .document(documentId)
            .set(session)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot written with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }
    }

}