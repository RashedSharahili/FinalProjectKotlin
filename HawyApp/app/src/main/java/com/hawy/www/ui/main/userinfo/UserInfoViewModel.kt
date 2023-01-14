package com.hawy.www.ui.main.userinfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hawy.www.data.model.session.Session
import com.hawy.www.data.model.userInfo.User
import com.hawy.www.domain.userInfo.EditUserInfoUseCase
import com.hawy.www.domain.userInfo.GetUserInfoUseCase
import com.hawy.www.domain.userInfo.GetUserSessionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserInfoViewModel(private val getUserInfoUseCase: GetUserInfoUseCase, private val editUserInfoUseCase: EditUserInfoUseCase, private val getUserSessionUseCase: GetUserSessionUseCase): ViewModel() {

    private var _auth = Firebase.auth
    val auth get() = _auth

    private var _userInfo = MutableStateFlow(User())
    val userInfo = _userInfo.asLiveData()

    private var _userSessions = MutableStateFlow<List<Session>>(emptyList())
    val userSession = _userSessions.asLiveData()

    fun getUserInfo() {

        viewModelScope.launch {
            getUserInfoUseCase.invoke().collect { userInfo ->

                    _userInfo.update { userInfo }

                Log.d("TAG", "getUserInfo: $userInfo")
                }
        }

    }

    fun editProfile(user: User) {

        viewModelScope.launch {

            editUserInfoUseCase.invoke(user)
        }
    }

    fun getUserSession() {

        viewModelScope.launch {

            getUserSessionUseCase.invoke().collect { userSession ->

                _userSessions.update { userSession }
            }
        }
    }

    fun logoutUser() {

        auth.signOut()
    }
}