package com.t4zb.aws.modellayer.rest.service.api


import com.t4zb.aws.modellayer.rest.service.event.MessageModel
import com.t4zb.aws.modellayer.rest.service.event.RegisterUserModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RegisterApi {

    @Headers("Content-Type: application/json")
    @POST("user/register")
    fun getRegisterWebService(@Body requestBody: RegisterUserModel): Call<MessageModel>
}