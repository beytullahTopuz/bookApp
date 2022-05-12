package com.t4zb.aws.modellayer.rest.service.repo

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.t4zb.aws.modellayer.rest.service.api.GetBookApi
import com.t4zb.aws.modellayer.rest.service.api.GetLoginApi
import com.t4zb.aws.modellayer.rest.service.event.BookModel
import com.t4zb.aws.modellayer.rest.service.event.MessageModel
import com.t4zb.aws.modellayer.rest.service.request.RetrofitClientInstance
import com.t4zb.aws.utils.showLogDebug
import com.t4zb.aws.utils.showLogError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookRepository (val app: Application){
    val bookListData = MutableLiveData<List<BookModel>>()


    @WorkerThread
    fun callWebServiceBook() {
        val retrofit = RetrofitClientInstance.buildRetrofit(app.applicationContext)
        val service = retrofit!!.create(GetBookApi::class.java)
        val call = service.getAllBook()

        call.enqueue(object :
            Callback<List<BookModel>> {
            override fun onResponse(
                call: Call<List<BookModel>>,
                response: Response<List<BookModel>>
            ) {
                if (response.isSuccessful) {
                    bookListData.postValue(response.body())
                    showLogDebug(TAG,response.body()!!.toString())

                }
            }
            override fun onFailure(call: Call<List<BookModel>>, t: Throwable) {
                showLogError(TAG, t.printStackTrace().toString())
            }
        })
    }
    init {
        CoroutineScope(Dispatchers.IO).launch {
            callWebServiceBook()
        }
    }
    companion object{
        const val TAG = "Book Repository"
    }
}