package com.hawy.www.utlis

import com.hawy.www.data.model.session.SessionRepository
import com.hawy.www.data.model.session.SessionRepositoryImp
import com.hawy.www.data.model.userInfo.UserRepository
import com.hawy.www.data.model.userInfo.UserRepositoryImp
import com.hawy.www.domain.session.AddSessionUseCase
import com.hawy.www.domain.session.GetSessionByFollowingUseCase
import com.hawy.www.domain.session.GetSessionUseCase
import com.hawy.www.domain.userInfo.EditUserInfoUseCase
import com.hawy.www.domain.userInfo.GetUserInfoUseCase
import com.hawy.www.domain.userInfo.GetUserSessionUseCase

fun providerSessionRepositoryImp(): SessionRepositoryImp =
    SessionRepositoryImp()

fun providerSessionRepository(): SessionRepository = SessionRepository(providerSessionRepositoryImp())

fun providerGetSessionUseCase(): GetSessionUseCase = GetSessionUseCase(providerSessionRepository())

fun providerGetSessionsByFollowings(): GetSessionByFollowingUseCase = GetSessionByFollowingUseCase(providerSessionRepository())

fun providerAddSession() : AddSessionUseCase = AddSessionUseCase(providerSessionRepository())

fun providerUserRepositoryImp(): UserRepositoryImp =
    UserRepositoryImp()

fun providerUserRepository(): UserRepository = UserRepository(providerUserRepositoryImp())

fun providerGetUserInfoUseCase(): GetUserInfoUseCase = GetUserInfoUseCase(providerUserRepository())

fun providerEditUserInfoUseCase() : EditUserInfoUseCase = EditUserInfoUseCase(providerUserRepository())

fun providerGetUserSessionUseCase() : GetUserSessionUseCase = GetUserSessionUseCase(
    providerUserRepository())