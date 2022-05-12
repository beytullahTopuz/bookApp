package com.t4zb.aws.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.t4zb.aws.modellayer.rest.service.event.BookModel
import com.t4zb.aws.modellayer.rest.service.repo.BookRepository
import com.t4zb.aws.modellayer.rest.service.repo.LoginRepository

class SharedViewModel (app: Application): AndroidViewModel(app){


  //  var loginDataRepo =LoginRepository(app)

    var bookListDataRepo = BookRepository(app)

    var userID: Int = 1
    var bookIndex : Int? = -1
    var bookID: Int? = -1

}