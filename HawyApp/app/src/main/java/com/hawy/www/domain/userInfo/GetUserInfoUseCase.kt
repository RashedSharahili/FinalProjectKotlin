package com.hawy.www.domain.userInfo

import com.hawy.www.data.model.userInfo.UserRepository

class GetUserInfoUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke() = userRepository.getUserInfo()
}