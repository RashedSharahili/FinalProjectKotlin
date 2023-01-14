package com.hawy.www.data.model.session

import com.hawy.www.data.model.session.Session
import kotlinx.coroutines.flow.Flow

interface SessionData {

    suspend fun getSessions() : Flow<List<Session>>

    suspend fun getUserFollowing() : List<String>

    suspend fun getSessionsByFollowings(userFollowing: List<String>) : Flow<List<Session>>

    suspend fun addSession(session: Session)
}