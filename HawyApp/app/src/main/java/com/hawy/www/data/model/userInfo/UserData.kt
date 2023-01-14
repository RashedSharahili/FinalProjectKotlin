package com.hawy.www.data.model.userInfo

import com.hawy.www.data.model.session.Session
import kotlinx.coroutines.flow.Flow

interface UserData {

    suspend fun getUserInfo(): Flow<User>

    suspend fun getUserFollowing(): Flow<List<String>>

    suspend fun editProfile(user: User)

    suspend fun getUserSession(): Flow<List<Session>>
}