package com.hawy.www.ui.main.userinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hawy.www.utlis.providerEditUserInfoUseCase
import com.hawy.www.utlis.providerGetUserInfoUseCase
import com.hawy.www.utlis.providerGetUserSessionUseCase

/**
 * Factory for constructing UserInfoViewModel with parameter
 */
class UserInfoViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserInfoViewModel::class.java)) {
            @Suppress("UNCHEKED_CAST")
            return UserInfoViewModel(
                providerGetUserInfoUseCase(),
                providerEditUserInfoUseCase(),
                providerGetUserSessionUseCase()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}