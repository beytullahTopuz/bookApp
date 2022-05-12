package com.t4zb.aws.modellayer.rest.service.repo

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.t4zb.aws.modellayer.rest.service.api.GetLoginApi
import com.t4zb.aws.modellayer.rest.service.api.RegisterApi
import com.t4zb.aws.modellayer.rest.service.event.MessageModel
import com.t4zb.aws.modellayer.rest.service.event.RegisterUserModel
import com.t4zb.aws.modellayer.rest.service.request.RetrofitClientInstance
import com.t4zb.aws.utils.showLogDebug
import com.t4zb.aws.utils.showLogError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRepository(val app: Application, var registerUserModel: RegisterUserModel) {

    var registerData = MutableLiveData<MessageModel>()



    @WorkerThread
    fun callWebServiceRegister() {
        val retrofit = RetrofitClientInstance.buildRetrofit(app.applicationContext)
        val service = retrofit!!.create(RegisterApi::class.java)
        val call = service.getRegisterWebService(registerUserModel)

        call.enqueue(object :
            Callback<MessageModel> {
            override fun onResponse(
                call: Call<MessageModel>,
                response: Response<MessageModel>
            ) {
                if (response.isSuccessful) {
                    registerData.postValue(response.body())
                    showLogDebug(TAG,response.body()!!.toString())

                }
            }
            override fun onFailure(call: Call<MessageModel>, t: Throwable) {
                showLogError(TAG, t.printStackTrace().toString())
            }
        })
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            callWebServiceRegister()
        }
    }
    companion object{
        const val TAG = "REGISTER REPOSITORY";
    }
}