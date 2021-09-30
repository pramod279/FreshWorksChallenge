package com.freshworks.challenge.model

/**
 * @Author: Pramod Selvaraj
 * @Date: 29.09.2021
 *
 * Giphy API Response Model
 */
data class GiphyData(
    val data: List<GifInfo>,
    val pagination: Pagination?
)

data class GifInfo(
    val id: String,
    val title: String,
    val images: GifImages,
    val isFavourite: Boolean
)

data class GifImages(
    val fixed_height: GifImage
)

data class GifImage(
    val url: String
)

data class Pagination(
    val count: Int?,
    val offset: Int?,
    val total_count: Int?
)