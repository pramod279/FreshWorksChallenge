package com.freshworks.challenge.views.trending.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.freshworks.challenge.BaseViewHolder
import com.freshworks.challenge.databinding.ListItemGiphyViewBinding
import com.freshworks.challenge.model.Data

/**
 * @Author: Pramod Selvaraj
 * @Date: 28.09.2021
 *
 * GIF Image Adapter Class For Loading All Gif Images
 */
class GifImageAdapter(private val clickListener: FavouritesClickListener) :
    PagingDataAdapter<Data, BaseViewHolder>(REPO_COMPARATOR) {
    /*Diff Util For Updating The Recycler View If Any Change In Data*/
    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            ListItemGiphyViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        /*Binding The Current Item To The View*/
        val gifImage = getItem(position)

        val itemBinding = holder.binding as ListItemGiphyViewBinding

        itemBinding.gifImage = gifImage
        itemBinding.favourite = clickListener
        itemBinding.executePendingBindings()
    }

    /*Favourites Gif Click Listener*/
    class FavouritesClickListener(val clickListener: (data: Data) -> Unit) {
        fun onClick(data: Data) = clickListener(data)
    }
}