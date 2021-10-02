package com.freshworks.challenge.ui.trending.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.airbnb.lottie.LottieAnimationView
import com.freshworks.challenge.data.entities.GifInfo
import com.freshworks.challenge.databinding.ItemGridGifViewBinding
import com.freshworks.challenge.databinding.ItemListGifViewBinding
import com.freshworks.challenge.utilities.vibrate


/**
 * @Author: Pramod Selvaraj
 * @Date: 29.09.2021
 *
 * Gif ViewHolder For Adapter ViewDataBinding
 */
sealed class GiphyViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    /*Grid Item View Holder for Trending Gifs*/
    class GridItemViewHolder(
        private val binding: ItemGridGifViewBinding,
        private val clickListener: FavouritesClickListener,
    ) : GiphyViewHolder(binding) {
        fun bind(gifImage: GifInfo) {
            binding.gifImage = gifImage
            binding.favourite = clickListener
        }
    }

    /*List Item View Holder For Searched Lists*/
    class ListItemViewHolder(
        private val binding: ItemListGifViewBinding,
        private val clickListener: FavouritesClickListener,
    ) : GiphyViewHolder(binding) {
        fun bind(gifImage: GifInfo) {
            binding.gifImage = gifImage
            binding.favourite = clickListener
        }
    }

    /*Favourites Gif Click Listener*/
    class FavouritesClickListener(val clickListener: (gifInfo: GifInfo) -> Unit) {
        fun onToggle(view: View, gifInfo: GifInfo) {
            (view as? LottieAnimationView)?.playAnimation()
            vibrate(view)
            clickListener(gifInfo)
        }
    }
}