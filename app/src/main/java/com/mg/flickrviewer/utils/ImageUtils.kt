package com.mg.flickrviewer.utils

import android.net.Uri
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun loadImage(imageUrl: Uri?, view: ImageView) {
    view.apply {
        if (tag == null || tag != imageUrl) {
            Picasso.get().load(imageUrl).into(this)
            tag = imageUrl
        }
    }

}