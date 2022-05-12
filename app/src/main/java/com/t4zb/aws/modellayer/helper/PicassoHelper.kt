package com.t4zb.aws.modellayer.helper

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

class PicassoHelper {

    fun picassoUtils(context: Context, pictureUrl: String, imageView: ImageView){
        Picasso.get().load(pictureUrl)
            .fit().centerCrop().into(imageView)
    }
    fun picassoOkhttp(context: Context, pictureUrl: String, imageView: ImageView) {
        val builder = Picasso.Builder(context)
        builder.downloader(OkHttp3Downloader(context))
        builder
            .build()
            .load(pictureUrl)
            .into(imageView)
    }

}