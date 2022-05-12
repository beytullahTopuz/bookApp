package com.t4zb.aws.modellayer.rest.service.api

import com.t4zb.aws.modellayer.rest.service.event.LoginModel
import com.t4zb.aws.modellayer.rest.service.event.MessageModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface GetLoginApi {

    @Headers("Content-Type: application/json")
    @POST("user/login")
    fun getLoginWebSevice(@Body requestBody: LoginModel): Call<MessageModel>
}