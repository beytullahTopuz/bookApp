package com.t4zb.aws.modellayer.rest.service.event

data class RegisterUserModel(
    val user_name: String,
    var user_lastname: String,
    var user_email: String,
    var user_password: String,
    var user_book_list: List<String>?
)