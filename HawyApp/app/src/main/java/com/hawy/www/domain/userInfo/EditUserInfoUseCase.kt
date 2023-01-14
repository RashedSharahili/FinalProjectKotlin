package com.hawy.www.domain.userInfo

import com.hawy.www.data.model.userInfo.User
import com.hawy.www.data.model.userInfo.UserRepository

class EditUserInfoUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(user: User) = userRepository.editProfile(user)
}