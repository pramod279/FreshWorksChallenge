package com.freshworks.challenge

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide

/**
 * @Author: Pramod Selvaraj
 * @Date: 29.09.2021
 *
 * UI Binder Class For Mapping Results To UI via Data Binding
 */
@BindingAdapter("loadImage")
fun loadImage(view: ImageView, imageUrl: String?) {
    /*Load Image URLs*/
    Glide.with(view.context)
        .load(imageUrl)
        .placeholder(showProgress(view))
        .error(R.drawable.ic_placeholder)
        .fallback(R.drawable.ic_placeholder)
        .into(view)
}

/*Function for Showing Progress Bar When Fetching Images*/
private fun showProgress(view: ImageView): CircularProgressDrawable {
    val progressLoader = CircularProgressDrawable(view.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
    }
    progressLoader.start()
    return progressLoader
}

/*Function for Toggling Favourites Animation*/
@BindingAdapter("toggleFavourites")
fun toggleFavourites(view: LottieAnimationView, isFavourite: Boolean) {
    when {
        isFavourite -> view.playAnimation()
        else -> view.cancelAnimation()
    }
}