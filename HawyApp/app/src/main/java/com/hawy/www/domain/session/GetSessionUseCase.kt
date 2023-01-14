package com.hawy.www.domain.session

import com.hawy.www.data.model.session.SessionRepository

class GetSessionUseCase(private val sessionRepository: SessionRepository) {

    suspend operator fun invoke() = sessionRepository.getSessions()
}