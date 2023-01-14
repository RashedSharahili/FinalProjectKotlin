package com.hawy.www.domain.session

import com.hawy.www.data.model.session.Session
import com.hawy.www.data.model.session.SessionRepository

class AddSessionUseCase(private val sessionRepository: SessionRepository) {

    suspend operator fun invoke(session: Session) = sessionRepository.addSession(session)
}