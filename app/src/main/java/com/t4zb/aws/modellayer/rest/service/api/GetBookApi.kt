package com.t4zb.aws.modellayer.rest.service.api

import com.t4zb.aws.modellayer.rest.service.event.BookModel
import com.t4zb.aws.modellayer.rest.service.event.BookModelInsert
import com.t4zb.aws.modellayer.rest.service.event.MessageModel
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET

import android.R.string.no




interface GetBookApi {

    @GET("book/getAllBook")
    fun getAllBook():Call<List<BookModel>>

    @Headers("Content-Type: application/json")
    @POST("book/insert")
    fun insetBook(@Body requestBody: BookModelInsert):Call<MessageModel>

    // update
    @Headers("Content-Type: application/json")
    @PATCH("book/patch/{id}")
    fun updateBook(@Body requestBody: BookModel, @Path("id") bookId: String):Call<MessageModel>

}