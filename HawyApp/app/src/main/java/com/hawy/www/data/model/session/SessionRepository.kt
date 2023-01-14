package com.hawy.www.data.model.session

class SessionRepository(private val sessionRepositoryImp: SessionRepositoryImp) {

    suspend fun getSessions() = sessionRepositoryImp.getSessions()

    private suspend fun getUserFollowing() = sessionRepositoryImp.getUserFollowing()

    suspend fun getSessionsByFollowings() = sessionRepositoryImp.getSessionsByFollowings(getUserFollowing())

    suspend fun addSession(session: Session) = sessionRepositoryImp.addSession(session)
}