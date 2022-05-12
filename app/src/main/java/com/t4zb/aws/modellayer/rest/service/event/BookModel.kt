package com.t4zb.aws.modellayer.rest.service.event

data class BookModel(
    val publisher_editor: String,
    var ideas: String,
    var book_name: String,
    var id: Int?,
    var imgUrl: String,
    var author: String
)
