package com.hawy.www.ui.main.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hawy.www.utlis.providerAddSession
import com.hawy.www.utlis.providerGetSessionUseCase
import com.hawy.www.utlis.providerGetSessionsByFollowings
import com.hawy.www.utlis.providerGetUserInfoUseCase

/**
 * Factory for constructing SessionViewModel with parameter
 */
class SessionViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SessionViewModel::class.java)) {
            @Suppress("UNCHEKED_CAST")
            return SessionViewModel(
                providerGetSessionUseCase(),
                providerGetSessionsByFollowings(),
                providerGetUserInfoUseCase(),
                providerAddSession()
            ) as T
        }
//        else if (modelClass.isAssignableFrom(PersonViewModel::class.java)) {
//            @Suppress("UNCHEKED_CAST")
//            return PersonViewModel(
//                providerSetPersonDataUseCase(), providerGetPersonDataUseCase(),
//                providerGetOnePersonDataUseCase(), providerSetOnePersonDataUseCase(),
//                providerDeletePersonDataUseCase(), providerGetIportedListUseCase()
//                , providerSearchUseCase()
//            ) as T
//        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}