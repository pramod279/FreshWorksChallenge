package com.freshworks.challenge.ui.trending.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.freshworks.challenge.data.entities.GifInfo
import com.freshworks.challenge.databinding.ItemGridGifViewBinding
import com.freshworks.challenge.databinding.ItemListGifViewBinding
import com.freshworks.challenge.ui.common.enums.ViewType
import com.freshworks.challenge.utilities.Constants.GRID_COLUMNS

/**
 * @Author: Pramod Selvaraj
 * @Date: 28.09.2021
 *
 * GIF Image Adapter Class For Loading All Gif Images
 */
class GifImageAdapter(
    private val layoutManager: GridLayoutManager,
    private val clickListener: FavouritesClickListener
) :
    PagingDataAdapter<GifInfo, GiphyViewHolder>(REPO_COMPARATOR) {
    /*Diff Util For Updating The Recycler View If Any Change In Data*/
    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<GifInfo>() {
            override fun areItemsTheSame(oldItem: GifInfo, newItem: GifInfo): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: GifInfo, newItem: GifInfo): Boolean =
                oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (layoutManager.spanCount == GRID_COLUMNS)
            ViewType.GRID.ordinal else ViewType.LIST.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiphyViewHolder {
        return when (viewType) {
            ViewType.GRID.ordinal -> GiphyViewHolder.GridItemViewHolder(
                ItemGridGifViewBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), clickListener
            )
            else -> GiphyViewHolder.ListItemViewHolder(
                ItemListGifViewBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), clickListener
            )
        }
    }

    override fun onBindViewHolder(holder: GiphyViewHolder, position: Int) {
        /*Binding The Current Item To The View*/
        val gifImage = getItem(position)

        when (holder) {
            is GiphyViewHolder.GridItemViewHolder -> holder.bind(gifImage as GifInfo)
            is GiphyViewHolder.ListItemViewHolder -> holder.bind(gifImage as GifInfo)
        }
    }

    /*Favourites Gif Click Listener*/
    class FavouritesClickListener(val clickListener: (gifInfo: GifInfo) -> Unit) {
        fun onClick(gifInfo: GifInfo) = clickListener(gifInfo)
    }
}