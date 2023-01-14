package com.hawy.www.data.model.userInfo

data class User(
    var bio: String? = "",
    var displayName: String? = "",
    var firstName: String? = "",
    var followingUsers: List<String> = arrayListOf(),
    var lastName: String? = "",
    var nickName: String? = "",
    var email: String? = "",
    var photoUri: String? = ""

)
