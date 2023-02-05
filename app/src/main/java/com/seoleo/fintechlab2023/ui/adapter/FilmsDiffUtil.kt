package com.seoleo.fintechlab2023.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.seoleo.fintechlab2023.ui.model.FilmUiModel


class FilmsDiffUtil(
    private val oldList: List<FilmUiModel>,
    private val newList: List<FilmUiModel>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].filmId == newList[newItemPosition].filmId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
}
