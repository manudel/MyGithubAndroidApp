package com.ed.mygithub.reposlist

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


object ReposListBindings {
    @BindingAdapter("app:reposItems")
    @JvmStatic
    fun setElements(recyclerView: RecyclerView, items: List<ShortRepo>) {
        with(recyclerView.adapter as ReposAdapter) {
            setItems(items)
        }
    }

    @JvmStatic
    @BindingAdapter("bind:imageUrl")
    fun loadImage(view: ImageView, url: String?) {
        url?.let {
            Glide.with(view.context).load(it).into(view)
        }
    }
}