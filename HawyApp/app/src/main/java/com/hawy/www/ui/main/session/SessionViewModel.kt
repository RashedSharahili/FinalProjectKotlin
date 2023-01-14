package com.hawy.www.ui.main.session

import android.util.Log
import androidx.lifecycle.*
import com.hawy.www.data.model.session.Session
import com.hawy.www.domain.session.AddSessionUseCase
import com.hawy.www.domain.session.GetSessionByFollowingUseCase
import com.hawy.www.domain.session.GetSessionUseCase
import com.hawy.www.domain.userInfo.GetUserInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SessionViewModel(private val getSessionUseCase: GetSessionUseCase, private val getSessionByFollowingUseCase: GetSessionByFollowingUseCase, private val getUserInfoUseCase: GetUserInfoUseCase, private val addSessionUseCase: AddSessionUseCase): ViewModel() {

    private var _sessions = MutableStateFlow<List<Session>>(emptyList())
    val session = _sessions.asLiveData()

    private var _userFollowing = MutableStateFlow<List<String>>(emptyList())
    val userFollowing = _userFollowing.asLiveData()

    private var _sessionsByFollowings = MutableStateFlow<List<Session>>(emptyList())
    val sessionsByFollowings = _sessionsByFollowings.asLiveData()

    fun getSessions() {
        viewModelScope.launch {

            getSessionUseCase.invoke().collect { sessions ->

                _sessions.update { sessions }
//                Log.d("TAG", "getSessions: $sessions")

            }
        }
    }

    fun getSessionsByFollowings(userFollowing: LiveData<List<String>>) {
        viewModelScope.launch {

            getSessionByFollowingUseCase.invoke().collect { sessions ->

                _sessionsByFollowings.update { sessions }
                Log.d("TAG", "getSessionsByFollowings: $sessions")
            }
        }
    }

    fun getUserFollowing(): List<String> {

        viewModelScope.launch {
            getUserInfoUseCase.invoke().collect { userFollowing ->

                _userFollowing.update { userFollowing.followingUsers }

                Log.d("TAG", "getUserInfo: $userFollowing")
            }
        }

        return _userFollowing.value
    }

    fun addSession(session: Session) {

        viewModelScope.launch {

            addSessionUseCase.invoke(session)
        }
    }

}