package com.hawy.www.data.model.userInfo

import com.hawy.www.data.model.session.SessionRepositoryImp

class UserRepository(private val userRepositoryImp: UserRepositoryImp) {

    suspend fun getUserInfo() = userRepositoryImp.getUserInfo()

    suspend fun getUserFollowing() = userRepositoryImp.getUserFollowing()

    suspend fun editProfile(user: User) = userRepositoryImp.editProfile(user)

    suspend fun getUserSession() = userRepositoryImp.getUserSession()
}