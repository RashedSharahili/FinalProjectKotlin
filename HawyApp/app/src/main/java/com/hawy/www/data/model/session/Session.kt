package com.hawy.www.data.model.session

data class Session(
    val category: String? = "",
    val date: String? = "",
    val details: String? = "",
    val isClosed: Boolean? = null,
    val lat: Double? = null,
    val long: Double? = null,
    val phoneNumber: String? = "",
    val sesstionID: String? = "",
    val timeInterval: Double? = null,
    val title: String? = "",
    val userID: String? = ""
)
