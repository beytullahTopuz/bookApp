package com.t4zb.aws.modellayer.rest.service.event

data class BookModelInsert(
    val publisher_editor: String,
    var ideas: String,
    var book_name: String,
    var imgUrl: String,
    var author: String
)
